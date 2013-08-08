package com.tzachz.github.commentcounter.api;

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
    private String html_url;

    public GHRepo() {
    }

    public String getName() {
        return name;
    }

    public String getHtml_url() {
        return html_url;
    }
}
