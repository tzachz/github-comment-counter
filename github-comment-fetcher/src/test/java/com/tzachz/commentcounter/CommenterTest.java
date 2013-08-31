package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 31/08/13
 * Time: 19:27
 */
public class CommenterTest {

    private final CommentBuilder builder = new CommentBuilder();

    @Test
    public void scoreEqualsZeroForNoComments() throws Exception {
        Commenter user = new Commenter("user");
        assertThat(user.getScore(), is(0));
    }

    @Test
    public void scoreEqualsOneForSingleComment() throws Exception {
        Commenter user = new Commenter("user");
        user.addComment(builder.createComment("user", ""), new GHRepo("repo"));
        assertThat(user.getScore(), is(1));
    }

    @Test
    public void scoreEqualsTwoForTwoCommentsOnOneRepo() throws Exception {
        Commenter user = new Commenter("user");
        user.addComment(builder.createComment("user", "u1"), new GHRepo("repo"));
        user.addComment(builder.createComment("user", "u2"), new GHRepo("repo"));
        assertThat(user.getScore(), is(2));
    }

    @Test
    public void scoreEqualsSixForTwoCommentsOnTwoRepos() throws Exception {
        Commenter user = new Commenter("user");
        user.addComment(builder.createComment("user", "u1"), new GHRepo("repo1"));
        user.addComment(builder.createComment("user", "u2"), new GHRepo("repo2"));
        assertThat(user.getScore(), is(6));
    }
}
