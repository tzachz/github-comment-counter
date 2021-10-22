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
        return emojisMap.getEmojiCodes().stream()
                .reduce(
                        comment,
                        (updated, emojiCode) -> {
                            final var emojiMarkdown = toEmojiMarkdown(emojiCode);
                            return updated.replace(emojiMarkdown, createImage(emojiMarkdown, emojisMap.getLink(emojiCode)));
                        },
                        (c1, c2) -> c2
                );
    }

    private String toEmojiMarkdown(String emojiCode) {
        return ":" + emojiCode + ":";
    }

    private String createImage(String emojiCode, String link) {
        return String.format("<img alt=\"%s\" src=\"%s\" height=\"20\" width=\"20\" align=\"absmiddle\">", emojiCode, link);
    }
}
