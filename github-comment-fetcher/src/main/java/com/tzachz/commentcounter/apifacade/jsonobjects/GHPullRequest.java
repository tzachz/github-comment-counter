package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 17:41
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHPullRequest {

    private final GHUser user;
    private final String url;

    @JsonCreator
    public GHPullRequest(@JsonProperty("user") GHUser user, @JsonProperty("url") String url) {
        this.user = user;
        this.url = url;
    }

    public GHUser getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GHPullRequest that = (GHPullRequest) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, url);
    }
}
