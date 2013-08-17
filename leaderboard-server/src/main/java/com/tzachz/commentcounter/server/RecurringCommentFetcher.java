package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.CommentFetcher;
import com.yammer.dropwizard.lifecycle.Managed;

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

    private final CommentFetcher commentFetcher;
    private final LeaderBoardStore store;
    private final long period;
    private final Timer timer;

    public RecurringCommentFetcher(CommentFetcher commentFetcher, LeaderBoardStore store, int refreshRate, TimeUnit timeUnit) {
        this.commentFetcher = commentFetcher;
        this.store = store;
        this.period = timeUnit.toMillis(refreshRate);
        this.timer = new Timer("comment-fetcher");
    }

    @Override
    public void start() throws Exception {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                store.set(commentFetcher.getCommentsByUser());
            }
        }, 0, period);
    }

    @Override
    public void stop() throws Exception {
        timer.cancel();
    }
}
