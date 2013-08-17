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
public class LeaderBoardRecord {

    private final String username;
    private final int commentCount;
    private final String sampleComment;

    @JsonCreator
    public LeaderBoardRecord(@JsonProperty("username") String username,
                             @JsonProperty("commentCount") int commentCount,
                             @JsonProperty("sampleComment") String sampleComment) {
        this.username = username;
        this.commentCount = commentCount;
        this.sampleComment = sampleComment;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaderBoardRecord that = (LeaderBoardRecord) o;

        if (commentCount != that.commentCount) return false;
        if (sampleComment != null ? !sampleComment.equals(that.sampleComment) : that.sampleComment != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + commentCount;
        result = 31 * result + (sampleComment != null ? sampleComment.hashCode() : 0);
        return result;
    }
}
