package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.apifacade.GitHubApiFacadeImpl;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

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
    }

    @Override
    public void run(LeaderBoardServerConfiguration configuration, Environment environment) throws Exception {
        GitHubCredentials gitHubCredentials = configuration.getGitHubCredentials();
        GitHubApiFacadeImpl gitHubApiFacade = new GitHubApiFacadeImpl(gitHubCredentials.getUsername(), gitHubCredentials.getPassword());
        CommentFetcher commentFetcher = new CommentFetcher(gitHubApiFacade, configuration.getOrganization(), configuration.getDaysBack());
        LeaderBoardStore store = new LeaderBoardStore();

        environment.addHealthCheck(new GitHubCredentialsHealthCheck(gitHubApiFacade, configuration.getOrganization()));
        environment.addResource(new LeaderBoardResource(store));
        environment.manage(new RecurringCommentFetcher(commentFetcher, store, configuration.getRefreshRateMinutes()));
    }
}
