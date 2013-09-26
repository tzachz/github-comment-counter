package com.tzachz.commentcounter;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

import java.util.List;
import java.util.Set;

import static com.google.common.primitives.Ints.max;

/**
* Created with IntelliJ IDEA.
* User: tzachz
* Date: 10/08/13
* Time: 13:58
*/
@SuppressWarnings("UnusedDeclaration")
public class Commenter {

    private final String username;
    private final List<GHComment> comments;
    private final Set<GHRepo> repos;

    public Commenter(String username) {
        this.username = username;
        this.comments = Lists.newArrayList();
        this.repos = Sets.newHashSet();
    }

    public void addComment(GHComment comment, GHRepo repo) {
        comments.add(comment);
        repos.add(repo);
    }

    public String getUsername() {
        return username;
    }

    public List<GHComment> getComments() {
        return ImmutableList.copyOf(comments);
    }

    public Set<GHRepo> getRepos() {
        return ImmutableSet.copyOf(repos);
    }

    public int getScore() {
        return comments.size() + max(0, (repos.size() - 1)) * 4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commenter commenter = (Commenter) o;

        if (!comments.equals(commenter.comments)) return false;
        if (!repos.equals(commenter.repos)) return false;
        if (!username.equals(commenter.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + comments.hashCode();
        result = 31 * result + repos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("comments", comments)
                .add("repos", repos)
                .toString();
    }
}
