package com.tzachz.commentcounter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static java.util.Collections.sort;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class CommentsByUser {

    private ConcurrentMap<String, Commenter> userComments = Maps.newConcurrentMap();

    public void addAll(Collection<GHComment> comments) {
        for (GHComment comment : comments) {
            String user = comment.getUser().getLogin();
            userComments.putIfAbsent(user, new Commenter(user));
            userComments.get(user).addComment(comment);
        }
    }

    public int getCommentCount(String user) {
        return userComments.get(user).getComments().size();
    }

    public List<Commenter> getCommentsByUser() {
        List<Commenter> commenters = Lists.newArrayList(userComments.values());
        sort(commenters);
        return commenters;
    }

    public int getSize() {
        return userComments.size();
    }
}
