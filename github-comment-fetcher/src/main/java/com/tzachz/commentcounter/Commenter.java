package com.tzachz.commentcounter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

import java.util.*;

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
    private final String userURL;
    private final Map<GHComment, GHRepo> comments;

    public Commenter(String username, String userURL) {
        this.username = username;
        this.userURL = userURL;
        this.comments = Maps.newHashMap();
    }

    public void addComment(GHComment comment, GHRepo repo) {
        comments.put(comment, repo);
    }

    public String getUsername() {
        return username;
    }

    public String getUserURL() {
        return userURL;
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
        return Objects.equals(username, commenter.username) &&
                Objects.equals(userURL, commenter.userURL) &&
                Objects.equals(comments, commenter.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userURL, comments);
    }

    @Override
    public String toString() {
        return "Commenter{" +
                "username='" + username + '\'' +
                ", userURL='" + userURL + '\'' +
                ", comments=" + comments +
                '}';
    }
}
