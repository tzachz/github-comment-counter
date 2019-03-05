package com.tzachz.commentcounter.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<String> repositories = new ArrayList<>();

    @JsonProperty
    private int refreshRateMinutes;

    @JsonProperty
    private String gitHubApiUrl = "https://api.github.com";

    public GitHubCredentials getGitHubCredentials() {
        return gitHubCredentials;
    }

    public String getOrganization() {
        return organization;
    }

    public Set<String> getRepositories() {
        return new HashSet<>(repositories);
    }

    public int getRefreshRateMinutes() {
        return refreshRateMinutes;
    }

    public String getGitHubApiUrl() {
        return gitHubApiUrl;
    }
}
