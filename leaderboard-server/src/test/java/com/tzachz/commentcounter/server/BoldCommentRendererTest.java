package com.tzachz.commentcounter.server;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BoldCommentRendererTest {

    @Test
    public void boldBecomesHtmlBold() throws Exception {
        String origin = "you **should not** do this";
        String result = new BoldCommentRenderer().render(origin);
        assertThat(result, is("you <b>should not</b> do this"));
    }
}