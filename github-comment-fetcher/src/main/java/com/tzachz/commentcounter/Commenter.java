package com.tzachz.commentcounter;

import com.google.common.base.Objects;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 10/08/13
* Time: 13:58
*/
public class Commenter implements Comparable<Commenter> {

    private final String username;
    private final Integer comments;

    public Commenter(String username, int comments) {
        this.username = username;
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public Integer getComments() {
        return comments;
    }

    @Override
    public int compareTo(Commenter o) {
        return o.comments.compareTo(comments); // descending by num of comments
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("comments", comments)
                .toString();
    }
}
