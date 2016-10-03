package com.tzachz.commentcounter;

import com.google.common.base.Objects;
import com.google.common.collect.*;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private final Map<GHComment, GHRepo> comments;

    public Commenter(String username) {
        this.username = username;
        this.comments = Maps.newHashMap();
    }

    public void addComment(GHComment comment, GHRepo repo) {
        comments.put(comment, repo);
    }

    public String getUsername() {
        return username;
    }

    public List<GHComment> getComments() {
        return ImmutableList.copyOf(comments.keySet());
    }

    public Set<GHRepo> getRepos() {
        return ImmutableSet.copyOf(comments.values());
    }

    public GHRepo getRepoFor(GHComment comment) {
        return comments.get(comment);
    }

    public Set<String> getPullRequests() {
        Set<String> pulls = new HashSet<>();
        for (GHComment ghComment : comments.keySet()) {
            pulls.add(ghComment.getPullRequestUrl());
        }
        return pulls;
    }

    public int getScore() {
        return comments.size() + max(0, (getRepos().size() - 1)) * 4;
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
