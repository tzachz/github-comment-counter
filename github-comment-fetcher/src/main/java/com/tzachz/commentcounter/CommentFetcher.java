package com.tzachz.commentcounter;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.GitHubTargetConfiguration;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

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
    private final GitHubTargetConfiguration configuration;
    private final int daysBack;
    private final PullRequestCache pullRequestCache;

    private Clock clock = new Clock();

    public CommentFetcher(GitHubApiFacade facade, GitHubTargetConfiguration configuration, int daysBack) {
        this.facade = facade;
        this.configuration = configuration;
        this.daysBack = daysBack;
        this.pullRequestCache = new PullRequestCache(facade);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public List<Comment> getComments() {
        final Date since = clock.getLocalDateNow().minusDays(this.daysBack).toDate();
        final Set<GHRepo> repos = getGhRepos();
        final List<Comment> comments = new ArrayList<>();
        for (GHRepo repo : repos) {
            comments.addAll(getRepoComments(since, repo));
        }
        logger.info("Fetched {} comments from {} repositories", comments.size(), repos.size());
        return comments;
    }

    private Set<GHRepo> getGhRepos() {
        return Sets.filter(facade.getOrgRepos(this.configuration.getOrganization()), new Predicate<GHRepo>() {
            @Override
            public boolean apply(GHRepo input) {
                final String pattern = configuration.getIncludeRepoPattern();
                final boolean matches = Pattern.matches(pattern, input.getName());
                if (!matches) logger.debug("ignoring repo %s: doesn't match pattern %s", input.getName(), pattern);
                return matches;
            }
        });
    }

    private Collection<Comment> getRepoComments(final Date since, final GHRepo repo) {
        Collection<GHComment> repoComments = facade.getRepoComments(configuration.getOrganization(), repo.getName(), since);
        Collection<GHComment> filteredComments = filterComments(since, repoComments);
        return Collections2.transform(filteredComments, new Function<GHComment, Comment>() {
            @Override
            public Comment apply(GHComment input) {
                return new Comment(input, repo);
            }
        });
    }

    private Collection<GHComment> filterComments(final Date since, Collection<GHComment> repoComments) {
        Predicate<GHComment> selfCommentsFilter = new Predicate<GHComment>() {
            @Override
            public boolean apply(GHComment input) {
                GHUser pullRequestUser = pullRequestCache.get(input.getPullRequestUrl()).getUser();
                return !pullRequestUser.equals(input.getUser());
            }
        };
        Predicate<GHComment> oldCommentsFilter = new Predicate<GHComment>() {
            @Override
            public boolean apply(GHComment input) {
                return input.getCreateDate().after(since);
            }
        };
        return filter(filter(repoComments, oldCommentsFilter), selfCommentsFilter);
    }

}
