package com.tzachz.commentcounter.server;

/**
 * Created by tzachz on 11/4/14
 */
public class CapSizeCommentRenderer implements CommentRenderer {

    private final static int MAX_COMMENT_LENGTH = 300;

    @Override
    public String render(String comment) {
        if (comment.length() > MAX_COMMENT_LENGTH) {
            comment = comment.substring(0, MAX_COMMENT_LENGTH -3) + "...";
        }
        return comment;
    }
}
