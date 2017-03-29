package com.tzachz.commentcounter;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

    public static final String ORG_NAME = "org";

    private final GHCommentBuilder commentBuilder = new GHCommentBuilder();
    private LocalDate now = new LocalDate(2013, 8, 8);

    @Mock
    private GitHubApiFacade facade;

    @Mock
    private Clock clock;

    private CommentFetcher counter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        counter = new CommentFetcher(facade, ORG_NAME, 1);
        counter.setClock(clock);
        when(clock.getLocalDateNow()).thenReturn(now);
    }

    @Test
    public void aggregatesRepos() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1", "repo2"));
        when(facade.getRepoComments(anyString(), anyString(), any(Date.class)))
                .thenReturn(commentBuilder.createEmptyComments("user1", "user2", "user1"));
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest(new GHUser("user3", "", "")));

        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(6));
        verify(facade).getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate());
        verify(facade).getRepoComments(ORG_NAME, "repo2", now.minusDays(1).toDate());
    }

    @Test
    public void commentOnSelfPullRequestIgnored() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user1", "", "")));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    @Test
    public void pullRequestNotFoundMeansCommentCounted() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("bad url"));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(1));
    }

    @Test
    public void oldCommentFilteredOut() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user2", "", "")));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(Arrays.asList(commentBuilder.createComment("user1", "url", now.minusDays(2).toDate())));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    private Set<GHRepo> getRepos(String... repoNames) {
        return Sets.newHashSet(transform(Arrays.asList(repoNames), new Function<String, GHRepo>() {
            @Override
            public GHRepo apply(String name) {
                return new GHRepo(name);
            }
        }));
    }

}
