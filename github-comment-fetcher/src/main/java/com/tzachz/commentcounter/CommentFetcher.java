package com.tzachz.commentcounter;

import com.google.common.base.Predicate;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Collections2.filter;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:46
 */
public class CommentFetcher {

    public static final Logger logger = LoggerFactory.getLogger(CommentFetcher.class);
    private final GitHubApiFacade facade;
    private final String organization;
    private final int daysBack;
    private final PullRequestCache pullRequestCache;

    private Clock clock = new Clock();

    public CommentFetcher(GitHubApiFacade facade, String organization, int daysBack) {
        this.facade = facade;
        this.organization = organization;
        this.daysBack = daysBack;
        this.pullRequestCache = new PullRequestCache(facade);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public List<Commenter> getCommentsByUser() {
        Date since = clock.getLocalDateNow().minusDays(this.daysBack).toDate();
        Set<String> repoNames = facade.getOrgRepoNames(this.organization);
        UserComments comments = new UserComments();
        for (String name : repoNames) {
            Collection<GHComment> repoComments = facade.getRepoComments(this.organization, name, since);
            Collection<GHComment> nonSelfComments = filter(repoComments, new Predicate<GHComment>() {
                @Override
                public boolean apply(GHComment input) {
                    GHUser pullRequestUser = pullRequestCache.get(input.getPull_request_url()).getUser();
                    return !pullRequestUser.equals(input.getUser());
                }
            });
            comments.addAll(nonSelfComments);
        }
        logger.info("Fetched comments by {} users from {} repositories", comments.getSize(), repoNames.size());
        return comments.getCommentsByUser();
    }


}
