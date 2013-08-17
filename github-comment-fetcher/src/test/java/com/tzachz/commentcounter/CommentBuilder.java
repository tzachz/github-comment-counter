package com.tzachz.commentcounter;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommentBuilder {

    Collection<GHComment> createComments(String... users) {
        List<GHComment> comments = new ArrayList<GHComment>();
        for (String user : users) {
            comments.addAll(Arrays.asList(new GHComment(user, "")));
        }
        return comments;
    }

    public Collection<GHComment> createComment(String username, String pullRequestURL) {
        return Lists.newArrayList(new GHComment(username, pullRequestURL));
    }
}