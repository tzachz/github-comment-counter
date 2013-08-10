package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.yammer.metrics.core.HealthCheck;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:04
 */
public class GitHubCredentialsHealthCheck extends HealthCheck {

    private final String organization;
    private final GitHubApiFacade gitHubApi;

    public GitHubCredentialsHealthCheck(GitHubApiFacade gitHubApiFacade, String organization) {
        super("github-creds");
        this.organization = organization;
        this.gitHubApi = gitHubApiFacade;
    }

    @Override
    protected Result check() throws Exception {
        try {
            gitHubApi.getOrg(organization);
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy(e);
        }
    }
}
