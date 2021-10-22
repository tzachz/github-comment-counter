package com.tzachz.commentcounter.server;


import com.tzachz.commentcounter.Clock;
import com.tzachz.commentcounter.Comment;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.EmojisMap;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 18:36
 */
public class LeaderBoardStore {

    private final Clock clock;

    private final Map<Period, List<Commenter>> store = new ConcurrentHashMap<>();
    private final EmojiStore emojiStore;

    public LeaderBoardStore(Clock clock, EmojiStore emojiStore) {
        this.clock = clock;
        this.emojiStore = emojiStore;
    }

    public void set(List<Comment> comments) {
        emojiStore.load();
        for (Period period : Period.values()) {
            store.put(period, aggregate(filterByRecency(comments, period.getDaysBack())));
        }
    }

    public List<Commenter> get(String name) {
        return isLoaded(name)? store.get(Period.valueOf(name.toUpperCase())) : Collections.emptyList();
    }

    public EmojisMap getEmojiMap() {
        return emojiStore.getMap();
    }

    public boolean isLoaded(String name) {
        return store.containsKey(Period.valueOf(name.toUpperCase()));
    }

    private Collection<Comment> filterByRecency(List<Comment> comments, int daysBack) {
        final Date since = clock.getLocalDateNow().minusDays(daysBack).toDate();
        return comments.stream().filter(input -> !input.getComment().getCreateDate().before(since)).collect(Collectors.toList());
    }

    private List<Commenter> aggregate(final Collection<Comment> comments) {
        Map<String, Commenter> userComments = new HashMap<>();
        for (Comment comment : comments) {
            final GHUser user = comment.getComment().getUser();
            String userLogin = user.getLogin();
            if (!userComments.containsKey(userLogin)) {
                userComments.put(userLogin, new Commenter(userLogin, user.getHtmlUrl()));
            }
            userComments.get(userLogin).addComment(comment.getComment(), comment.getRepo());
        }
        return List.copyOf(userComments.values());
    }
}
