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

    @JsonCreator
    public LeaderBoardRecord(@JsonProperty("username") String username, @JsonProperty("commentCount") int commentCount) {
        this.username = username;
        this.commentCount = commentCount;
    }

    public String getUsername() {
        return username;
    }

    public int getCommentCount() {
        return commentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaderBoardRecord record = (LeaderBoardRecord) o;

        if (commentCount != record.commentCount) return false;
        if (username != null ? !username.equals(record.username) : record.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + commentCount;
        return result;
    }
}
