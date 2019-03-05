package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:46
 */
public class CommentFetcher {

    private static final Logger logger = LoggerFactory.getLogger(CommentFetcher.class);

    private final GitHubApiFacade facade;
    private final String organization;
    private final Set<String> repositories;
    private final int daysBack;
    private final PullRequestCache pullRequestCache;

    private Clock clock = new Clock();
    private final Predicate<GHComment> selfCommentsFilter;

    public CommentFetcher(GitHubApiFacade facade, String organization, Set<String> repositories, int daysBack) {
        this.facade = facade;
        this.organization = organization;
        this.repositories = Collections.unmodifiableSet(repositories);
        this.daysBack = daysBack;
        this.pullRequestCache = new PullRequestCache(facade);
        this.selfCommentsFilter = comment -> {
            GHUser pullRequestUser = pullRequestCache.get(comment.getPullRequestUrl()).getUser();
            return !pullRequestUser.equals(comment.getUser());
        };
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public List<Comment> getComments() {
        final Date since = clock.getLocalDateNow().minusDays(this.daysBack).toDate();
        Set<GHRepo> repos = facade.getOrgRepos(this.organization);
        List<GHRepo> interestingRepos = repos.stream()
                .filter(this::isInterestingRepo)
                .collect(Collectors.toList());
        List<Comment> comments = new ArrayList<>();
        for (GHRepo repo : interestingRepos) {
            comments.addAll(getRepoComments(since, repo));
        }
        logger.info("Fetched {} comments from {} repositories", comments.size(), interestingRepos.size());
        return comments;
    }

    private Collection<Comment> getRepoComments(final Date since, final GHRepo repo) {
        Collection<GHComment> repoComments = facade.getRepoComments(this.organization, repo.getName(), since);

        // increase cache hit rate by preemptively fetching last month's PRs,
        // which probably account for most of last month's comments
        if (repoComments.size() > 20) {
            pullRequestCache.putAll(facade.getPullRequests(this.organization, repo.getName(), since));
        }

        return repoComments.stream()
                .filter(input1 -> input1.getCreateDate().after(since))
                .filter(selfCommentsFilter)
                .map(input -> new Comment(input, repo))
                .collect(Collectors.toList());
    }

    private boolean isInterestingRepo(GHRepo repo) {
        if (repositories.isEmpty()) {
            /*
             * If no repositories are specified, all repositories are
             * interesting.
             */
            return true;
        }
        return repositories.contains(repo.getName());
    }
}
