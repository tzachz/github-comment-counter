package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:05
 */
public class GitHubCredentials extends Configuration {

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private String token;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

}
