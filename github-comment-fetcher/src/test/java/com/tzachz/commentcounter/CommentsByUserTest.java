package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:28
 */
public class CommentsByUserTest {

    private final CommentBuilder commentBuilder = new CommentBuilder();

    private final GHRepo repo = new GHRepo("my-repo");

    @Test
    public void aggregateUSerComments() throws Exception {
        CommentsByUser commentsByUser = new CommentsByUser();
        commentsByUser.addAll(commentBuilder.createEmptyComments("aa", "aa", "bb"), repo);
        commentsByUser.addAll(commentBuilder.createEmptyComments("bb", "cc"), repo);
        assertThat(commentsByUser.getCommentCount("aa"), is(2));
        assertThat(commentsByUser.getCommentCount("bb"), is(2));
        assertThat(commentsByUser.getCommentCount("cc"), is(1));
    }

    @Test
    public void leaderBoardSortedDescending() throws Exception {
        CommentsByUser commentsByUser = new CommentsByUser();
        commentsByUser.addAll(commentBuilder.createEmptyComments("aa", "bb", "aa"), repo);
        List<Commenter> leaderBoard = commentsByUser.getCommentsByUser();
        assertThat(leaderBoard.get(0).getComments().size(), equalTo(2));
    }

}
