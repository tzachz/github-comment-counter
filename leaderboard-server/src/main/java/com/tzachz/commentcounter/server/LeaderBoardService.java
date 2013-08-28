package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
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
        final LeaderBoardStore store = new LeaderBoardStore();
        environment.addHealthCheck(new GitHubCredentialsHealthCheck(gitHubApiFacade, configuration.getOrganization()));
        environment.addResource(new LeaderBoardResource(store, configuration.getOrganization()));
        startFetchers(buildRunnables(store, getFetchers(configuration, gitHubApiFacade)),
                environment,
                configuration.getRefreshRateMinutes());


    }

    private Iterable<Runnable> buildRunnables(final LeaderBoardStore store, Map<String, CommentFetcher> fetchers) {
        return Iterables.transform(fetchers.entrySet(), new Function<Map.Entry<String, CommentFetcher>, Runnable>() {
            @Override
            public Runnable apply(final Map.Entry<String, CommentFetcher> fetcherEntry) {
                return new Runnable() {
                    @Override
                    public void run() {
                        store.set(fetcherEntry.getKey(), fetcherEntry.getValue().getCommentsByUser());
                    }
                };
            }
        });
    }

    private void startFetchers(Iterable<Runnable> fetchers, Environment environment, int refreshRateMinutes) {
        ScheduledExecutorService executorService = environment.managedScheduledExecutorService("comment-fetcher", 1);
        for (final Runnable fetcher : fetchers) {
            executorService.scheduleAtFixedRate(fetcher, 0, refreshRateMinutes, TimeUnit.MINUTES);
        }
    }

    private Map<String, CommentFetcher> getFetchers(LeaderBoardServerConfiguration configuration, GitHubApiFacade gitHubApiFacade) {
        return ImmutableMap.of(
                "today", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 1),
                "week", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 7),
                "month", new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), 30)
        );
    }
}
