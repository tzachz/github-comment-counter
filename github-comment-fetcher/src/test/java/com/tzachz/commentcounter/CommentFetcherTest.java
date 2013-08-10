package com.tzachz.commentcounter;

import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:20
 */
public class CommentFetcherTest {

    private final CommentBuilder commentBuilder = new CommentBuilder();

    @Test
    public void leaderBoardAggregatesRepos() throws Exception {
        GitHubApiFacade facade = mock(GitHubApiFacade.class);
        when(facade.getOrgRepoNames("org")).thenReturn(Sets.newHashSet("repo1", "repo2"));
        when(facade.getRepoComments(anyString(), anyString(), any(Date.class)))
                .thenReturn(commentBuilder.createComments("user1", "user2", "user1"));

        CommentFetcher counter = new CommentFetcher(facade, "org", 1);
        List<Commenter> board = counter.getCommentsLeaderBoard();
        assertThat(board, hasSize(2));
        assertThat(board, hasItems(new Commenter("user1", 4), new Commenter("user2", 2)));
    }
}
