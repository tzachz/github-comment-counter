package com.tzachz.commentcounter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.sort;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class UserComments {

    private ConcurrentMap<String, AtomicInteger> userComments = Maps.newConcurrentMap();

    public void addAll(Collection<GHComment> comments) {
        for (GHComment comment : comments) {
            String user = comment.getUser().getLogin();
            userComments.putIfAbsent(user, new AtomicInteger(0));
            userComments.get(user).incrementAndGet();
        }
    }

    public int getCommentCount(String user) {
        return userComments.get(user).intValue();
    }

    public List<Commenter> getLeaderBoard() {
        List<Commenter> commenters = Lists.newArrayList();
        for (Map.Entry<String, AtomicInteger> user : userComments.entrySet()) {
            commenters.add(new Commenter(user.getKey(), user.getValue().intValue()));
        }
        sort(commenters);
        return commenters;
    }

}
