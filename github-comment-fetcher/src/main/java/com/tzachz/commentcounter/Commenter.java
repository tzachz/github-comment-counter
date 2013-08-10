package com.tzachz.commentcounter;

import com.google.common.base.Objects;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 10/08/13
* Time: 13:58
*/
@SuppressWarnings("UnusedDeclaration")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commenter commenter = (Commenter) o;

        if (!comments.equals(commenter.comments)) return false;
        if (!username.equals(commenter.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + comments.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("comments", comments)
                .toString();
    }
}
