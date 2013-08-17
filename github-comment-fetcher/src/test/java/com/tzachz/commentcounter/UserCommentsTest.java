package com.tzachz.commentcounter;

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
public class UserCommentsTest {

    private final CommentBuilder commentBuilder = new CommentBuilder();

    @Test
    public void aggregateUSerComments() throws Exception {
        UserComments userComments = new UserComments();
        userComments.addAll(commentBuilder.createComments("aa", "aa", "bb"));
        userComments.addAll(commentBuilder.createComments("bb", "cc"));
        assertThat(userComments.getCommentCount("aa"), is(2));
        assertThat(userComments.getCommentCount("bb"), is(2));
        assertThat(userComments.getCommentCount("cc"), is(1));
    }

    @Test
    public void leaderBoardSortedDescending() throws Exception {
        UserComments userComments = new UserComments();
        userComments.addAll(commentBuilder.createComments("aa", "bb", "aa"));
        List<Commenter> leaderBoard = userComments.getCommentsByUser();
        assertThat(leaderBoard.get(0), equalTo(new Commenter("aa", 2)));
    }

}
