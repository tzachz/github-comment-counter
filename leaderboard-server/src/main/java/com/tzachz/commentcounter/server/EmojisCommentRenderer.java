package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.EmojisMap;

/**
 * Created by tzachz on 11/4/14
 */
public class EmojisCommentRenderer implements CommentRenderer {

    private final EmojisMap emojisMap;

    public EmojisCommentRenderer(EmojisMap emojisMap) {
        this.emojisMap = emojisMap;
    }

    @Override
    public String render(String comment) {
        for (String emojiCode : emojisMap.getEmojiCodes()) {
            String emojiMarkdown = toEmojiMarkdown(emojiCode);
            comment = comment.replace(emojiMarkdown, createImage(emojiMarkdown, emojisMap.getLink(emojiCode)));
        }
        return comment;
    }

    private String toEmojiMarkdown(String emojiCode) {
        return ":" + emojiCode + ":";
    }

    private String createImage(String emojiCode, String link) {
        return String.format("<img alt=\"%s\" src=\"%s\" height=\"20\" width=\"20\" align=\"absmiddle\">", emojiCode, link);
    }
}
