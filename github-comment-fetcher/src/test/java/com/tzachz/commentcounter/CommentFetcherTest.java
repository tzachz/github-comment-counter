package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:20
 */
public class CommentFetcherTest {

    private static final String ORG_NAME = "org";

    private static final Set<String> REPOSITORIES = Collections.emptySet();

    private final GHCommentBuilder commentBuilder = new GHCommentBuilder();
    private LocalDate now = new LocalDate(2013, 8, 8);

    @Mock
    private GitHubApiFacade facade;

    @Mock
    private Clock clock;

    private CommentFetcher counter;

    @Before
    public void setUp() {
        initMocks(this);
        counter = new CommentFetcher(facade, ORG_NAME, REPOSITORIES, 1);
        counter.setClock(clock);
        when(clock.getLocalDateNow()).thenReturn(now);
    }

    @Test
    public void aggregatesRepos() {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1", "repo2"));
        when(facade.getRepoComments(anyString(), anyString(), any(Date.class)))
                .thenReturn(commentBuilder.createEmptyComments("user1", "user2", "user1"));
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest(new GHUser("user3", "", ""), ""));

        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(6));
        verify(facade).getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate());
        verify(facade).getRepoComments(ORG_NAME, "repo2", now.minusDays(1).toDate());
    }

    @Test
    public void commentOnSelfPullRequestIgnored() {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user1", "", ""), ""));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    @Test
    public void pullRequestNotFoundMeansCommentCounted() {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("bad url"));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(1));
    }

    @Test
    public void oldCommentFilteredOut() {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user2", "", ""), ""));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(Collections.singletonList(commentBuilder.createComment("user1", "url", now.minusDays(2).toDate())));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    /**
     * Asserts that when {@link CommentFetcher} is given a list of
     * repositories, it considers only those repositories in the
     * entire set of repos in an org.  The assertion on an empty set
     * of repositories is done by other tests where {@link * CommentFetcher}
     * is created with an empty set.
     */
    @Test
    public void interestingRepos() {
        /*
         * repo1 and repo3 will be returned by the orgRepos.
         * repoNotFound won't be.
         */
        List<String> interestingRepos = Arrays.asList("repo1", "repoNotFound", "repo3");
        counter = new CommentFetcher(facade, ORG_NAME, new LinkedHashSet<>(interestingRepos), 1);
        counter.setClock(clock);

        when(clock.getLocalDateNow()).thenReturn(now);
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1", "repoSkip", "repo3"));
        when(facade.getRepoComments(anyString(), anyString(), any(Date.class)))
                .thenReturn(Collections.singletonList(commentBuilder.createComment("user1", "", now.toDate())));
        List<Comment> comments = counter.getComments();

        assertThat(comments, hasSize(2));
        verify(facade).getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate());
        verify(facade).getRepoComments(ORG_NAME, "repo3", now.minusDays(1).toDate());

        /*
         * Assert that facade.getRepoComments was called exactly 2
         * times -- once for each 'interesting' repo.
         */
        verify(facade, times(2))
                .getRepoComments(anyString(), anyString(), any(Date.class));
    }

    private Set<GHRepo> getRepos(String... repoNames) {
        return Arrays.stream(repoNames).map(GHRepo::new).collect(Collectors.toSet());
    }

}
