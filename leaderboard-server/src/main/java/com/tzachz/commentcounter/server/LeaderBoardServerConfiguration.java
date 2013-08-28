package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:33
 */
public class LeaderBoardServerConfiguration extends Configuration {

    @JsonProperty
    private GitHubCredentials gitHubCredentials;
    @JsonProperty
    private String organization;

    @JsonProperty
    private int refreshRateMinutes;

    public GitHubCredentials getGitHubCredentials() {
        return gitHubCredentials;
    }

    public String getOrganization() {
        return organization;
    }

    public int getRefreshRateMinutes() {
        return refreshRateMinutes;
    }
}
