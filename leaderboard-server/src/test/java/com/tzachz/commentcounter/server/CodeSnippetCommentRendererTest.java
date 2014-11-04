package com.tzachz.commentcounter.server;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CodeSnippetCommentRendererTest {

    @Test
    public void codeSnippetsTransformedToHtmlCodeBlock() throws Exception {
        String comment = "try ```some(code);``` and then ```some(more);```!";
        String formatted = new CodeSnippetCommentRenderer().render(comment);
        assertThat(formatted, is("try <code>some(code);</code> and then <code>some(more);</code>!"));
    }

}