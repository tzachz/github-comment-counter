package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.List;
import java.util.Random;

class CommenterToRecordTransformer implements Function<Commenter, LeaderBoardRecord> {

    private final static Random random = new Random();

    private final List<CommentRenderer> renderers;

    CommenterToRecordTransformer(List<CommentRenderer> renderers) {
        this.renderers = renderers;
    }

    @Override
    public LeaderBoardRecord apply(Commenter commenter) {
        GHComment randomComment = getRandomComment(commenter);
        return new LeaderBoardRecord(
                commenter.getUsername(),
                commenter.getUserURL(),
                commenter.getScore(),
                commenter.getComments().size(),
                commenter.getRepos().size(),
                commenter.getPullRequests().size(),
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
        for (CommentRenderer renderer : renderers) {
            body = renderer.render(body);
        }
        return body;
    }

}
