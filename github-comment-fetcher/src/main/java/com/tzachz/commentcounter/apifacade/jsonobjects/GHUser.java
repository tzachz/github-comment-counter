package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:44
 * A very partial representation of a GitHub user
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHUser {

    private final String login;
    private final String htmlUrl;
    private final String avatarUrl;

    @JsonCreator
    public GHUser(@JsonProperty("login") String login,
                  @JsonProperty("html_url") String htmlUrl,
                  @JsonProperty("avatar_url") String avatarUrl) {
        this.login = login;
        this.htmlUrl = htmlUrl;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
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
