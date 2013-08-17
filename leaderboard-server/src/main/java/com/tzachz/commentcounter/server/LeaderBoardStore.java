package com.tzachz.commentcounter.server;


import com.tzachz.commentcounter.Commenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 18:36
 */
public class LeaderBoardStore {

    private final AtomicReference<List<Commenter>> store = new AtomicReference<List<Commenter>>(new ArrayList<Commenter>());
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    public void set(List<Commenter> commenters) {
        store.set(commenters);
        loaded.set(true);
    }

    public List<Commenter> get() {
        return store.get();
    }

    public boolean isLoaded() {
        return loaded.get();
    }
}
