package com.tzachz.commentcounter.server;

/**
 * Created by tzachz on 11/4/14
 */
public class CodeSnippetCommentRenderer extends MarkdownToHtmlTagCommentRenderer {

    public static final String CODE_BOUNDARY = "```";

    public CodeSnippetCommentRenderer() {
        super(CODE_BOUNDARY, "code");
    }

}
