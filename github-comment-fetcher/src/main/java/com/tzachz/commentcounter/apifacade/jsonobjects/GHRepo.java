package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:38
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHRepo {

    private String name;

    public GHRepo() {
    }

    public String getName() {
        return name;
    }

}
