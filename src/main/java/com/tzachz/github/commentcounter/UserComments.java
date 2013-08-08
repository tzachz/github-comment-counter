package com.tzachz.github.commentcounter;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tzachz.github.commentcounter.api.GHEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.Collections2.filter;
import static java.util.Collections.sort;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class UserComments {

    private static final Set<String> COUNTED_EVENT_TYPES = Sets.newHashSet("PullRequestReviewCommentEvent", "IssueCommentEvent");

    private ConcurrentMap<String, AtomicInteger> userComments = Maps.newConcurrentMap();

    public void addAll(Collection<GHEvent> events) {
        Collection<GHEvent> comments = filter(events, new Predicate<GHEvent>() {
            @Override
            public boolean apply(GHEvent input) {
                return COUNTED_EVENT_TYPES.contains(input.getType());
            }
        });
        for (GHEvent comment : comments) {
            String user = comment.getActor().getLogin();
            userComments.putIfAbsent(user, new AtomicInteger(0));
            userComments.get(user).incrementAndGet();
        }
    }

    public void addAll(UserComments other) {
        for (String user : other.userComments.keySet()) {
            userComments.putIfAbsent(user, new AtomicInteger(0));
            userComments.get(user).addAndGet(other.userComments.get(user).intValue());
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

    public class Commenter implements Comparable<Commenter> {

        final String username;
        final Integer comments;

        public Commenter(String username, int comments) {
            this.username = username;
            this.comments = comments;
        }

        @Override
        public int compareTo(Commenter o) {
            return o.comments.compareTo(comments); // descending by num of comments
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("username", username)
                    .add("comments", comments)
                    .toString();
        }
    }

}
