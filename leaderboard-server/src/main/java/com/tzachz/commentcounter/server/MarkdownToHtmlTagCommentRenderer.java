package com.tzachz.commentcounter.server;

/**
 * Created by tzachz on 11/4/14
 */
public class MarkdownToHtmlTagCommentRenderer implements CommentRenderer {

    private final String markdownBoundaryRegex;
    private final String htmlTag;

    public MarkdownToHtmlTagCommentRenderer(String markdownBoundaryRegex, String htmlTag) {
        this.markdownBoundaryRegex = markdownBoundaryRegex;
        this.htmlTag = htmlTag;
    }

    @Override
    public final String render(String comment) {
        return replaceMarkdownWithHtml(comment);
    }

    private String replaceMarkdownWithHtml(String comment) {
        final String transformed = comment
                .replaceFirst(markdownBoundaryRegex, "<" + htmlTag + ">")
                .replaceFirst(markdownBoundaryRegex, "</" + htmlTag + ">");
        if (transformed.equals(comment)) {
            return comment;
        } else {
            return replaceMarkdownWithHtml(transformed);
        }
    }
}
