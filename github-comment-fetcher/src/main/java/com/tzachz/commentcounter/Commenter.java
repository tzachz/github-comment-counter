package com.tzachz.commentcounter;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 10/08/13
* Time: 13:58
*/
@SuppressWarnings("UnusedDeclaration")
public class Commenter implements Comparable<Commenter> {

    private final String username;
    private final List<GHComment> comments;

    public Commenter(String username) {
        this.username = username;
        this.comments = Lists.newArrayList();
    }

    public void addComment(GHComment comment) {
        comments.add(comment);
    }

    public String getUsername() {
        return username;
    }

    public List<GHComment> getComments() {
        return ImmutableList.copyOf(comments);
    }

    @Override
    public int compareTo(Commenter o) {
        return Integer.valueOf(o.comments.size()).compareTo(comments.size()); // descending by num of comments
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
