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

    private final GHCommentBuilder builder = new GHCommentBuilder();

    @Test
    public void scoreEqualsZeroForNoComments() {
        Commenter user = new Commenter("user", "");
        assertThat(user.getScore(), is(0));
    }

    @Test
    public void scoreEqualsOneForSingleComment() {
        Commenter user = new Commenter("user", "");
        user.addComment(builder.createComment("user", ""), new GHRepo("repo"));
        assertThat(user.getScore(), is(1));
    }

    @Test
    public void scoreEqualsTwoForTwoCommentsOnOneRepo() {
        Commenter user = new Commenter("user", "");
        user.addComment(builder.createComment("user", "u1"), new GHRepo("repo"));
        user.addComment(builder.createComment("user", "u2"), new GHRepo("repo"));
        assertThat(user.getScore(), is(2));
    }

    @Test
    public void scoreEqualsSixForTwoCommentsOnTwoRepos() {
        Commenter user = new Commenter("user", "");
        user.addComment(builder.createComment("user", "u1"), new GHRepo("repo1"));
        user.addComment(builder.createComment("user", "u2"), new GHRepo("repo2"));
        assertThat(user.getScore(), is(6));
    }
}
