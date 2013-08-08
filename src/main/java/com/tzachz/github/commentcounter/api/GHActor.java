package com.tzachz.github.commentcounter.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:44
 * A very partial representation of a GitHub Actor (user)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHActor {

    private String login;
    private String url;

    public GHActor() {
    }

    public GHActor(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getUrl() {
        return url;
    }
}
