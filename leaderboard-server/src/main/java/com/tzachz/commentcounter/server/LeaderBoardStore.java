package com.tzachz.commentcounter.server;


import com.tzachz.commentcounter.Commenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 18:36
 */
public class LeaderBoardStore {

    private final Map<String, List<Commenter>> store = new ConcurrentHashMap<>();

    public void set(String name, List<Commenter> commenters) {
        store.put(name, commenters);
    }

    public List<Commenter> get(String name) {
        return isLoaded(name)? store.get(name) : Collections.<Commenter>emptyList();
    }

    public boolean isLoaded(String name) {
        return store.containsKey(name);
    }
}
