package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 17/08/13
* Time: 19:02
*/
@SuppressWarnings("UnusedDeclaration")
public class LeaderBoardRecord implements Comparable<LeaderBoardRecord> {

    private final String username;
    private final String userURL;
    private final int score;
    private final int commentCount;
    private final int repoCount;
    private final int pullCount;
    private final String sampleComment;
    private final String sampleCommentUrl;
    private final String sampleCommentRepo;
    private final String avatarUrl;

    @JsonCreator
    public LeaderBoardRecord(@JsonProperty("username") String username,
                             @JsonProperty("userURL") String userURL,
                             @JsonProperty("score") int score,
                             @JsonProperty("commentCount") int commentCount,
                             @JsonProperty("repoCount") int repoCount,
                             @JsonProperty("pullCount") int pullCount,
                             @JsonProperty("sampleComment") String sampleComment,
                             @JsonProperty("sampleCommentUrl") String sampleCommentUrl,
                             @JsonProperty("sampleCommentRepo") String sampleCommentRepo,
                             @JsonProperty("avatarUrl") String avatarUrl) {
        this.username = username;
        this.userURL = userURL;
        this.score = score;
        this.commentCount = commentCount;
        this.repoCount = repoCount;
        this.pullCount = pullCount;
        this.sampleComment = sampleComment;
        this.sampleCommentUrl = sampleCommentUrl;
        this.sampleCommentRepo = sampleCommentRepo;
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getUserURL() {
        return userURL;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getSampleComment() {
        return sampleComment;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getSampleCommentUrl() {
        return sampleCommentUrl;
    }

    public int getScore() {
        return score;
    }

    public int getRepoCount() {
        return repoCount;
    }

    public int getPullCount() { return pullCount; }

    public String getSampleCommentRepo() {
        return sampleCommentRepo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderBoardRecord that = (LeaderBoardRecord) o;
        return score == that.score &&
                commentCount == that.commentCount &&
                repoCount == that.repoCount &&
                pullCount == that.pullCount &&
                Objects.equals(username, that.username) &&
                Objects.equals(userURL, that.userURL) &&
                Objects.equals(sampleComment, that.sampleComment) &&
                Objects.equals(sampleCommentUrl, that.sampleCommentUrl) &&
                Objects.equals(sampleCommentRepo, that.sampleCommentRepo) &&
                Objects.equals(avatarUrl, that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userURL, score, commentCount, repoCount, pullCount, sampleComment, sampleCommentUrl, sampleCommentRepo, avatarUrl);
    }

    @Override
    public int compareTo(LeaderBoardRecord o) {
        return Integer.valueOf(o.getScore()).compareTo(getScore()); // descending by score
    }
}
