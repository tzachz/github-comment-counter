package com.tzachz.github.commentcounter.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 * A very partial representation of a GitHub Event
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHComment {

    private GHUser user;

    @SuppressWarnings("UnusedDeclaration")
    public GHComment() {
    }

    public GHComment(GHUser user) {
        this.user = user;
    }

    public GHUser getUser() {
        return user;
    }

}
