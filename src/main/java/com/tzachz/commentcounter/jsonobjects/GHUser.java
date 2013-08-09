package com.tzachz.commentcounter.jsonobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:44
 * A very partial representation of a GitHub user
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHUser {

    private String login;

    @SuppressWarnings("UnusedDeclaration")
    public GHUser() {
    }

    public GHUser(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

}
