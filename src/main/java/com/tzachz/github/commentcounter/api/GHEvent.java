package com.tzachz.github.commentcounter.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 * A very partial representation of a GitHub Event
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHEvent {

    private String type;
    private GHActor actor;
    private Object payload;

    @SuppressWarnings("UnusedDeclaration")
    public GHEvent() {
    }

    public GHEvent(String type, GHActor actor) {
        this.type = type;
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public GHActor getActor() {
        return actor;
    }

    public Object getPayload() {
        return payload;
    }
}
