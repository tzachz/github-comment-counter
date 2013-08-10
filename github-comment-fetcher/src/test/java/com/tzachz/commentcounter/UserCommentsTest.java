package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import org.junit.Test;

import java.util.ArrayList;
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
    public void aggregateUSerComments() throws Exception {
        UserComments userComments = new UserComments();
        userComments.addAll(createComments("aa", "aa", "bb"));
        userComments.addAll(createComments("bb", "cc"));
        assertThat(userComments.getCommentCount("aa"), is(2));
        assertThat(userComments.getCommentCount("bb"), is(2));
        assertThat(userComments.getCommentCount("cc"), is(1));
    }

    @Test
    public void leaderBoardSortedDescending() throws Exception {
        UserComments userComments = new UserComments();
        userComments.addAll(createComments("aa", "bb", "aa"));
        List<Commenter> leaderBoard = userComments.getLeaderBoard();
        assertThat(leaderBoard.get(0).getUsername(), is("aa"));
    }

    private List<GHComment> createComments(String... users) {
        List<GHComment> comments = new ArrayList<>();
        for (String user : users) {
            comments.addAll(Arrays.asList(new GHComment(new GHUser(user))));
        }
        return comments;
    }


}
