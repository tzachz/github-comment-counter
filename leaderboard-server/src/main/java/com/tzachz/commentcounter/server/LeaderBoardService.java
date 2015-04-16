package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Clock;
import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.apifacade.GitHubApiFacadeImpl;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

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
        GitHubApiFacadeImpl apiFacade = getApiFacade(configuration);
        LeaderBoardStore store = getStore(apiFacade);

        environment.addHealthCheck(new GitHubCredentialsHealthCheck(apiFacade, configuration.getOrganization()));
        environment.addResource(new LeaderBoardResource(store, configuration.getOrganization()));

        createScheduledFetcher(configuration, environment, apiFacade, store);
    }

    private GitHubApiFacadeImpl getApiFacade(LeaderBoardServerConfiguration configuration) {
        GitHubCredentials credentials = configuration.getGitHubCredentials();
        if (credentials.isTokenBased()) {
            return new GitHubApiFacadeImpl(credentials.getToken());
        }
        else {
            return new GitHubApiFacadeImpl(credentials.getUsername(), credentials.getPassword());
        }
    }

    private LeaderBoardStore getStore(GitHubApiFacadeImpl apiFacade) {
        EmojiStore emojiStore = new EmojiStore(apiFacade);
        return new LeaderBoardStore(new Clock(), emojiStore);
    }

    private void createScheduledFetcher(LeaderBoardServerConfiguration configuration, Environment environment, GitHubApiFacadeImpl apiFacade, LeaderBoardStore store) {
        ScheduledExecutorService executorService = environment.managedScheduledExecutorService("comment-fetcher", 1);
        final CommentFetcher fetcher = new CommentFetcher(apiFacade, configuration.getOrganization(), Period.getLongest().getDaysBack());
        executorService.scheduleAtFixedRate(new FetcherRunnable(store, fetcher), 0, configuration.getRefreshRateMinutes(), TimeUnit.MINUTES);
    }

}
