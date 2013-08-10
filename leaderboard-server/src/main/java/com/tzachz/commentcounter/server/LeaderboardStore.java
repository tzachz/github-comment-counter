package com.tzachz.commentcounter.server;


import com.tzachz.commentcounter.Commenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 18:36
 */
public class LeaderBoardStore {

    private final AtomicReference<List<Commenter>> store = new AtomicReference<List<Commenter>>(new ArrayList<Commenter>());

    public void set(List<Commenter> commenters) {
        store.set(commenters);
    }

    public List<Commenter> get() {
        return store.get();
    }
}
