package com.tzachz.commentcounter.apifacade;

import java.util.Map;

public class EmojisResource extends GitHubResource {

    protected EmojisResource(String username, String password) {
        super(username, password);
    }

    protected EmojisResource(String token) {
        super(token);
    }

    public EmojisMap getEmojisMap() {
        Map<String, String> emojis = (Map<String, String>)getResource().path("emojis").get(Object.class);
        return new EmojisMap(emojis);
    }

}
