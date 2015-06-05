package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tzachz.commentcounter.apifacade.GitHubTargetConfiguration;
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
    private GitHubTargetConfiguration gitHubTarget;

    @JsonProperty
    private int refreshRateMinutes;

    public GitHubCredentials getGitHubCredentials() {
        return gitHubCredentials;
    }

    public GitHubTargetConfiguration getGitHubTarget() {
        return gitHubTarget;
    }

    public int getRefreshRateMinutes() {
        return refreshRateMinutes;
    }
}
