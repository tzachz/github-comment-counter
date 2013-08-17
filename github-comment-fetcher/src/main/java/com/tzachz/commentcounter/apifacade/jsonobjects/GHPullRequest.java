package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 17:41
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHPullRequest {

    private GHUser user;

    @SuppressWarnings("UnusedDeclaration")
    public GHPullRequest() {
    }

    public GHPullRequest(String user) {
        this.user = new GHUser(user);
    }

    public GHUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GHPullRequest that = (GHPullRequest) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
