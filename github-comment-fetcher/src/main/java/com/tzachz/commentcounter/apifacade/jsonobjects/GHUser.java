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

    private final int id;
    private final String login;
    private final String avatarUrl;

    @JsonCreator
    public GHUser(@JsonProperty("id") int id, @JsonProperty("login") String login, @JsonProperty("avatar_url") String avatarUrl) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GHUser ghUser = (GHUser) o;

        if (id != ghUser.id) return false;
        if (login != null ? !login.equals(ghUser.login) : ghUser.login != null) return false;
        return avatarUrl != null ? avatarUrl.equals(ghUser.avatarUrl) : ghUser.avatarUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }
}
