package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.GenericType;

import java.util.Map;

public class EmojisResource extends GitHubResource {

    public EmojisResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    public EmojisMap getEmojisMap() {
        Map<String, String> emojis = getResource().path("emojis").get(new GenericType<Map<String, String>>() {});
        return new EmojisMap(emojis);
    }

}
