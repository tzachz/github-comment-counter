package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.GitHubApiFacadeImpl;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:41
 */
public class LeaderboardService extends Service<LeaderboardServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new LeaderboardService().run(args);
    }

    @Override
    public void initialize(Bootstrap<LeaderboardServerConfiguration> bootstrap) {
        bootstrap.setName("Leaderboard-Server");
    }

    @Override
    public void run(LeaderboardServerConfiguration configuration, Environment environment) throws Exception {
        GitHubCredentials gitHubCredentials = configuration.getGitHubCredentials();
        GitHubApiFacadeImpl gitHubApiFacade = new GitHubApiFacadeImpl(gitHubCredentials.getUsername(), gitHubCredentials.getPassword());
        environment.addResource(new LeaderboardResource());
        environment.addHealthCheck(new GitHubCredentialsHealthCheck(gitHubApiFacade, configuration.getOrganization()));
    }
}
