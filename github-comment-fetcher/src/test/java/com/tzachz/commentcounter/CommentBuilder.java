package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentBuilder {

    List<GHComment> createComments(String... users) {
        List<GHComment> comments = new ArrayList<GHComment>();
        for (String user : users) {
            comments.addAll(Arrays.asList(new GHComment(new GHUser(user))));
        }
        return comments;
    }
}