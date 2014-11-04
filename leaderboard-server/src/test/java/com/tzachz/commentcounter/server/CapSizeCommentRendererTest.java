package com.tzachz.commentcounter.server;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CapSizeCommentRendererTest {

    private CapSizeCommentRenderer renderer = new CapSizeCommentRenderer();

    @Test
    public void shortCommentUnchanged() throws Exception {
        String shortString = ":+1:";
        assertThat(renderer.render(shortString), is(shortString));
    }

    @Test
    public void longCommentsChoppedAfter300Chars() throws Exception {
        String tooLong = "Lorem ipsum dolor sit amet, " +
                "consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
                "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat " +
                "nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                "mollit anim id est laborum.";
        String comment = renderer.render(tooLong);
        assertThat(comment, endsWith("..."));
        assertThat(comment.length(), CoreMatchers.is(300));
    }

}