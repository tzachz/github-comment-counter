package com.tzachz.commentcounter;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;

import java.util.*;

public class GHCommentBuilder {

    public Collection<GHComment> createEmptyComments(String... users) {
        List<GHComment> comments = new ArrayList<>();
        for (String user : users) {
            comments.addAll(Arrays.asList(createComment(user, "")));
        }
        return comments;
    }

    public Collection<GHComment> createCommentCollection(String username, String pullRequestURL) {
        return Lists.newArrayList(createComment(username, pullRequestURL));
    }

    public GHComment createComment(String username, String pullRequestURL) {
        return createComment(username, pullRequestURL, "", "");
    }

    public GHComment createComment(String username, String pullRequestURL, String body) {
        return createComment(username, pullRequestURL, body, "");
    }

    public GHComment createComment(String username, String pullRequestURL, Date createDate) {
        return new GHComment()
            .setUser(new GHUser(username, ""))
            .setPullRequestUrl(pullRequestURL)
            .setBody("")
            .setHtmlUrl("")
            .setCreateDate(createDate);
    }

    public GHComment createComment(String username, String pullRequestURL, String body, String avatarURL) {
        return new GHComment()
            .setUser(new GHUser(username, avatarURL))
            .setPullRequestUrl(pullRequestURL)
            .setBody(body)
            .setHtmlUrl("")
            .setCreateDate(new Date());
    }
}
