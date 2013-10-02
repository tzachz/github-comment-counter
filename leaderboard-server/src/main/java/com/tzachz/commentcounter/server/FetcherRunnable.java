package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.CommentFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Created by IntelliJ IDEA.
* User: tzachz
* Date: 10/2/13
*/
class FetcherRunnable implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(FetcherRunnable.class);

    private final LeaderBoardStore store;
    private final CommentFetcher fetcher;

    public FetcherRunnable(LeaderBoardStore store, CommentFetcher fetcher) {
        this.store = store;
        this.fetcher = fetcher;
    }

    @Override
    public void run() {
        try {
            store.set(fetcher.getComments());
        } catch (Exception e) {
            logger.error("error fetching and storing comments - abandoning result", e);
        }
    }
}
