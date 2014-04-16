package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.EmojisMap;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.List;
import java.util.Random;

class CommenterToRecordTransformer implements Function<Commenter, LeaderBoardRecord> {

    private final static int MAX_COMMENT_LENGTH = 300;
    private final static Random random = new Random();

    private final EmojisMap emojisMap;

    CommenterToRecordTransformer(EmojisMap emojisMap) {
        this.emojisMap = emojisMap;
    }

    @Override
    public LeaderBoardRecord apply(Commenter commenter) {
        GHComment randomComment = getRandomComment(commenter);
        return new LeaderBoardRecord(
                commenter.getUsername(),
                commenter.getScore(),
                commenter.getComments().size(),
                commenter.getRepos().size(),
                renderCommentBody(randomComment.getBody()),
                randomComment.getHtmlUrl(),
                commenter.getRepoFor(randomComment).getName(),
                randomComment.getUser().getAvatarUrl());
    }

    private GHComment getRandomComment(Commenter commenter) {
        List<GHComment> comments = commenter.getComments();
        return comments.get(random.nextInt(comments.size()));
    }

    private String renderCommentBody(String body) {
        body = capSize(body);
        body = renderEmojis(body);
        return body;
    }

    private String capSize(String body) {
        if (body.length() > MAX_COMMENT_LENGTH) {
            body = body.substring(0, MAX_COMMENT_LENGTH -3) + "...";
        }
        return body;
    }

    private String renderEmojis(String body) {
        for (String emojiCode : emojisMap.getEmojiCodes()) {
            String emojiMarkdown = toEmojiMarkdown(emojiCode);
            body = body.replace(emojiMarkdown, createImage(emojiMarkdown, emojisMap.getLink(emojiCode)));
        }
        return body;
    }

    private String toEmojiMarkdown(String emojiCode) {
        return ":" + emojiCode + ":";
    }

    private String createImage(String emojiCode, String link) {
        return String.format("<img alt=\"%s\" src=\"%s\" height=\"20\" width=\"20\" align=\"absmiddle\">", emojiCode, link);
    }
}
