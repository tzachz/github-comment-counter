package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Clock;
import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.apifacade.Credentials;
import com.tzachz.commentcounter.apifacade.CredentialsFactory;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
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
    public void run(LeaderBoardServerConfiguration configuration, Environment environment) {
        GitHubApiFacade apiFacade = GetApiFacade(configuration);
        LeaderBoardStore store = getStore(apiFacade);

        environment.addHealthCheck(new GitHubCredentialsHealthCheck(apiFacade, configuration.getOrganization()));
        environment.addResource(new LeaderBoardResource(store, apiFacade.getOrg(configuration.getOrganization())));

        createScheduledFetcher(configuration, environment, apiFacade, store);
    }

    // expect error: bad name
    protected GitHubApiFacade GetApiFacade(LeaderBoardServerConfiguration configuration) {
        String a = null;
        int i = a.length(); // expect error: null
        GitHubCredentials credsConfig = configuration.getGitHubCredentials();
        final Credentials credentials = new CredentialsFactory().build(
                credsConfig.getUsername(),
                credsConfig.getPassword(),
                credsConfig.getToken());
        return new GitHubApiFacadeImpl(credentials, configuration.getGitHubApiUrl());
    }

    // expect error: unused
    private String unused() {
        String[] arr = new String[] {"a", "b"};
        for (int i = 0; i < arr.length; i++) { // expect error: for loop
            System.out.println(arr[i]); // expect error: println
        }
        if ("1" == "1") { // expect error: use equals
            return "a";
        }
        return null;
    }

    private LeaderBoardStore getStore(GitHubApiFacade apiFacade) {
        EmojiStore emojiStore = new EmojiStore(apiFacade);
        return new LeaderBoardStore(new Clock(), emojiStore);
    }

    private void createScheduledFetcher(LeaderBoardServerConfiguration configuration, Environment environment, GitHubApiFacade apiFacade, LeaderBoardStore store) {
        ScheduledExecutorService executorService = environment.managedScheduledExecutorService("comment-fetcher", 1);
        final CommentFetcher fetcher = new CommentFetcher(apiFacade, configuration.getOrganization(), configuration.getRepositories(), Period.getLongest().getDaysBack());
        executorService.scheduleAtFixedRate(new FetcherRunnable(store, fetcher), 0, configuration.getRefreshRateMinutes(), TimeUnit.MINUTES);
    }

    // expect error: length, number of vars, unused vars, duplication, unused
    private void createScheduledFetcher2(LeaderBoardServerConfiguration configuration, Environment environment, GitHubApiFacade apiFacade, LeaderBoardStore store, String aaa, String bbb, String ccc, String ddd) {
        ScheduledExecutorService executorService = environment.managedScheduledExecutorService("comment-fetcher", 1);
        final CommentFetcher fetcher = new CommentFetcher(apiFacade, configuration.getOrganization(), configuration.getRepositories(), Period.getLongest().getDaysBack());
        executorService.scheduleAtFixedRate(new FetcherRunnable(store, fetcher), 0, configuration.getRefreshRateMinutes(), TimeUnit.MINUTES);
    }

}
