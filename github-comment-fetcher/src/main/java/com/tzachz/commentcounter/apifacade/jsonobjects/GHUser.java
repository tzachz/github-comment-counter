package com.tzachz.commentcounter.apifacade.jsonobjects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GHUser ghUser = (GHUser) o;

        if (login != null ? !login.equals(ghUser.login) : ghUser.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
