package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.GenericType;

import java.util.Map;

class EmojisResource extends GitHubResource {

    EmojisResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    EmojisMap getEmojisMap() {
        final Map<String, String> emojis = getResource().path("emojis").get(new GenericType<Map<String, String>>() {});
        return new EmojisMap(emojis);
    }

}
