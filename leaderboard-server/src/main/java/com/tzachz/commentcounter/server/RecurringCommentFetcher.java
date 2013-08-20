package com.tzachz.commentcounter.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.tzachz.commentcounter.CommentFetcher;
import com.yammer.dropwizard.lifecycle.Managed;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 17:33
 */
public class RecurringCommentFetcher implements Managed {

    private final Map<String, CommentFetcher> commentFetchers;
    private final LeaderBoardStore store;
    private final long period;
    private final Timer timer;

    public RecurringCommentFetcher(Map<String, CommentFetcher> commentFetchers, LeaderBoardStore store, int refreshRate, TimeUnit timeUnit) {
        this.commentFetchers = commentFetchers;
        this.store = store;
        this.period = timeUnit.toMillis(refreshRate);
        this.timer = new Timer("comment-fetcher");
    }

    @Override
    public void start() throws Exception {
        for (final Map.Entry<String, CommentFetcher> entry : commentFetchers.entrySet()) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    store.set(entry.getKey(), entry.getValue().getCommentsByUser());
                }
            }, 0, period);
        }
    }

    @Override
    public void stop() throws Exception {
        timer.cancel();
    }
}
