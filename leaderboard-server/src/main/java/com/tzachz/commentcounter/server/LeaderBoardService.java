package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.GitHubApiFacadeImpl;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:41
 */
public class LeaderBoardService extends Service<LeaderBoardServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new LeaderBoardService().run(args);
    }

    @Override
    public void initialize(Bootstrap<LeaderBoardServerConfiguration> bootstrap) {
        bootstrap.setName("Leaderboard-Server");
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(LeaderBoardServerConfiguration configuration, Environment environment) throws Exception {
        GitHubCredentials gitHubCredentials = configuration.getGitHubCredentials();
        GitHubApiFacadeImpl gitHubApiFacade = new GitHubApiFacadeImpl(gitHubCredentials.getUsername(), gitHubCredentials.getPassword());

        LeaderBoardStore store = new LeaderBoardStore();

        environment.addHealthCheck(new GitHubCredentialsHealthCheck(gitHubApiFacade, configuration.getOrganization()));
        environment.addResource(new LeaderBoardResource(store, configuration.getOrganization()));
        environment.manage(new RecurringCommentFetcher(getFetchers(configuration, gitHubApiFacade),
                store, configuration.getRefreshRateMinutes(), TimeUnit.MINUTES));
    }

    private Map<String, CommentFetcher> getFetchers(LeaderBoardServerConfiguration configuration, GitHubApiFacade gitHubApiFacade) {
        return ImmutableMap.of(
                "today", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 1),
                "week", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 7),
                "month", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 30)
        );
    }
}
