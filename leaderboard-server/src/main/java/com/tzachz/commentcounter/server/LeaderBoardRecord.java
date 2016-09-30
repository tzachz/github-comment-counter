package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 17/08/13
* Time: 19:02
*/
@SuppressWarnings("UnusedDeclaration")
public class LeaderBoardRecord implements Comparable<LeaderBoardRecord> {

    private final String username;
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
                             @JsonProperty("score") int score,
                             @JsonProperty("commentCount") int commentCount,
                             @JsonProperty("repoCount") int repoCount,
                             @JsonProperty("pullCount") int pullCount,
                             @JsonProperty("sampleComment") String sampleComment,
                             @JsonProperty("sampleCommentUrl") String sampleCommentUrl,
                             @JsonProperty("sampleCommentRepo") String sampleCommentRepo,
                             @JsonProperty("avatarUrl") String avatarUrl) {
        this.username = username;
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

        if (commentCount != that.commentCount) return false;
        if (repoCount != that.repoCount) return false;
        if (pullCount != that.pullCount) return false;
        if (score != that.score) return false;
        if (avatarUrl != null ? !avatarUrl.equals(that.avatarUrl) : that.avatarUrl != null) return false;
        if (sampleComment != null ? !sampleComment.equals(that.sampleComment) : that.sampleComment != null)
            return false;
        if (sampleCommentRepo != null ? !sampleCommentRepo.equals(that.sampleCommentRepo) : that.sampleCommentRepo != null)
            return false;
        if (sampleCommentUrl != null ? !sampleCommentUrl.equals(that.sampleCommentUrl) : that.sampleCommentUrl != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + score;
        result = 31 * result + commentCount;
        result = 31 * result + repoCount;
        result = 31 * result + pullCount;
        result = 31 * result + (sampleComment != null ? sampleComment.hashCode() : 0);
        result = 31 * result + (sampleCommentUrl != null ? sampleCommentUrl.hashCode() : 0);
        result = 31 * result + (sampleCommentRepo != null ? sampleCommentRepo.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(LeaderBoardRecord o) {
        return Integer.valueOf(o.getScore()).compareTo(getScore()); // descending by score
    }
}
