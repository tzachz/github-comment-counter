package com.tzachz.commentcounter.server;

/**
 * Created by tzachz on 11/4/14
 */
public class BoldCommentRenderer extends MarkdownToHtmlTagCommentRenderer {

    public BoldCommentRenderer() {
        super("\\*\\*", "b");
    }
}
