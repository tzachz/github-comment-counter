package com.tzachz.commentcounter.server;


import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzachz.commentcounter.Clock;
import com.tzachz.commentcounter.Comment;
import com.tzachz.commentcounter.Commenter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 18:36
 */
public class LeaderBoardStore {

    private final Clock clock;

    private final Map<Period, List<Commenter>> store = new ConcurrentHashMap<>();

    public LeaderBoardStore(Clock clock) {
        this.clock = clock;
    }

    public void set(List<Comment> comments) {
        for (Period period : Period.values()) {
            store.put(period, aggregate(filterByRecency(comments, period.getDaysBack())));
        }
    }

    public List<Commenter> get(String name) {
        return isLoaded(name)? store.get(Period.valueOf(name)) : Collections.<Commenter>emptyList();
    }

    public boolean isLoaded(String name) {
        return store.containsKey(Period.valueOf(name));
    }

    private Collection<Comment> filterByRecency(List<Comment> comments, int daysBack) {
        final Date since = clock.getLocalDateNow().minusDays(daysBack).toDate();
        return Collections2.filter(comments, new Predicate<Comment>() {
            @Override
            public boolean apply(Comment input) {
                return !input.getComment().getCreateDate().before(since);
            }
        });
    }

    private List<Commenter> aggregate(final Collection<Comment> comments) {
        Map<String, Commenter> userComments = Maps.newHashMap();
        for (Comment comment : comments) {
            String user = comment.getComment().getUser().getLogin();
            if (!userComments.containsKey(user)) {
                userComments.put(user, new Commenter(user));
            }
            userComments.get(user).addComment(comment.getComment(), comment.getRepo());
        }
        return Lists.newArrayList(userComments.values());
    }
}
