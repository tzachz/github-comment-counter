package com.tzachz.commentcounter;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

    private final CommentBuilder commentBuilder = new CommentBuilder();
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
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest(new GHUser("user3", "")));

        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasSize(2));
        assertThat(board, hasItems(new CommenterMatcher("user1", 4), new CommenterMatcher("user2", 2)));
    }

    @Test
    public void commentOnSelfPullRequestIgnored() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user1", "")));
        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasSize(0));
    }

    @Test
    public void pullRequestNotFoundMeansCommentCounted() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("bad url"));
        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasItem(new CommenterMatcher("user1", 1)));
    }

    @Test
    public void oldCommentFilteredOut() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos("repo1"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user2", "")));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(Arrays.asList(commentBuilder.createComment("user1", "url", now.minusDays(2).toDate())));
        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasSize(0));
    }

    private Set<GHRepo> getRepos(String... repoNames) {
        return Sets.newHashSet(transform(Arrays.asList(repoNames), new Function<String, GHRepo>() {
            @Override
            public GHRepo apply(String name) {
                return new GHRepo(name);
            }
        }));
    }

    private static class CommenterMatcher extends BaseMatcher<Commenter> {

        private final String expectedUser;
        private final int expectedCommentCount;

        public CommenterMatcher(String expectedUser, int expectedCommentCount) {
            this.expectedUser = expectedUser;
            this.expectedCommentCount = expectedCommentCount;
        }

        @Override
        public boolean matches(Object item) {
            return item instanceof Commenter &&
                    ((Commenter)item).getUsername().equals(expectedUser) &&
                    ((Commenter)item).getComments().size() == expectedCommentCount;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Commenter with user = ")
                    .appendValue(expectedUser)
                    .appendText(" and comment count = ")
                    .appendValue(expectedCommentCount);
        }
    }
}
