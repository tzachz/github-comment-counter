package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by tzachz on 3/14/17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHOrg {

    private final String name;
    private final String htmlUrl;

    @JsonCreator
    public GHOrg(@JsonProperty("name") String name, @JsonProperty("html_url") String htmlUrl) {
        this.name = name;
        this.htmlUrl = htmlUrl;
    }

    public String getName() {
        return name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GHOrg ghOrg = (GHOrg) o;
        return Objects.equals(name, ghOrg.name) &&
                Objects.equals(htmlUrl, ghOrg.htmlUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, htmlUrl);
    }
}
