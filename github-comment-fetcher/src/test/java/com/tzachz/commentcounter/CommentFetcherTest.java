package com.tzachz.commentcounter;

import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
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
        when(facade.getOrgRepoNames(ORG_NAME)).thenReturn(Sets.newHashSet("repo1", "repo2"));
        when(facade.getRepoComments(anyString(), anyString(), any(Date.class)))
                .thenReturn(commentBuilder.createComments("user1", "user2", "user1"));
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest("user3"));

        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasSize(2));
        assertThat(board, hasItems(new Commenter("user1", 4), new Commenter("user2", 2)));
    }

    @Test
    public void commentOnSelfPullRequestIgnored() throws Exception {
        when(facade.getOrgRepoNames(ORG_NAME)).thenReturn(Sets.newHashSet("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createComment("user1", "body", "url"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest("user1"));
        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasSize(0));
    }

    @Test
    public void pullRequestNotFoundMeansCommentCounted() throws Exception {
        when(facade.getOrgRepoNames(ORG_NAME)).thenReturn(Sets.newHashSet("repo1"));
        when(facade.getRepoComments(ORG_NAME, "repo1", now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createComment("user1", "body", "url"));
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("bad url"));
        List<Commenter> board = counter.getCommentsByUser();
        assertThat(board, hasItem(new Commenter("user1", 1)));
    }
}
