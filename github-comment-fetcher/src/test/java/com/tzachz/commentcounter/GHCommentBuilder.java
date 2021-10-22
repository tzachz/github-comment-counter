package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;

import java.util.*;

public class GHCommentBuilder {

    public Collection<GHComment> createEmptyComments(String... users) {
        List<GHComment> comments = new ArrayList<>();
        for (String user : users) {
            comments.addAll(Collections.singletonList(createComment(user, "")));
        }
        return comments;
    }

    public Collection<GHComment> createCommentCollection(String username, String pullRequestURL) {
        return List.of(createComment(username, pullRequestURL));
    }

    public GHComment createComment(String username, String pullRequestURL) {
        return createComment(username, pullRequestURL, "", "");
    }

    public GHComment createComment(String username, String pullRequestURL, String body) {
        return createComment(username, pullRequestURL, body, "");
    }

    public GHComment createComment(String username, String pullRequestURL, Date createDate) {
        return new GHComment(new GHUser(username, "", ""), pullRequestURL, "", "", createDate);
    }

    public GHComment createComment(String username, String pullRequestURL, String body, String avatarURL) {
        return new GHComment(new GHUser(username, "", avatarURL), pullRequestURL, body, "", new Date());
    }
}