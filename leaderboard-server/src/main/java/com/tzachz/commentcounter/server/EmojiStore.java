package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.EmojisMap;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class EmojiStore {

    public static final EmojisMap EMPTY_MAP = new EmojisMap(new HashMap<>());

    private final GitHubApiFacade apiFacade;

    public EmojiStore(GitHubApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    private final AtomicReference<EmojisMap> map = new AtomicReference<>(EMPTY_MAP);

    public EmojisMap getMap() {
        return map.get();
    }

    public void load() {
        if (map.get() == EMPTY_MAP) {
            map.compareAndSet(EMPTY_MAP, apiFacade.getEmojiMap());
        }
    }

}
