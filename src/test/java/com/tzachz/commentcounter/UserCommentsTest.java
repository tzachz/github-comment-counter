package com.tzachz.commentcounter;

import com.tzachz.commentcounter.jsonobjects.GHUser;
import com.tzachz.commentcounter.jsonobjects.GHComment;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:28
 */
public class UserCommentsTest {

    @Test
    public void leaderBoardSortedDescending() throws Exception {
        UserComments userComments = createUserComments("aa", "bb", "aa");
        List<UserComments.Commenter> leaderBoard = userComments.getLeaderBoard();
        assertThat(leaderBoard.get(0).username, is("aa"));
    }

    @Test
    public void anotherUserCommentAggregated() throws Exception {
        UserComments userComments = createUserComments("aa", "aa", "bb");
        UserComments another = createUserComments("bb", "cc");
        userComments.addAll(another);
        assertThat(userComments.getCommentCount("aa"), is(2));
        assertThat(userComments.getCommentCount("bb"), is(2));
        assertThat(userComments.getCommentCount("cc"), is(1));
    }

    private UserComments createUserComments(String... users) {
        UserComments userComments = new UserComments();
        for (String user : users) {
            userComments.addAll(Arrays.asList(new GHComment(new GHUser(user))));
        }
        return userComments;
    }


}
