package com.tzachz.commentcounter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 18:10
 */
public class PullRequestCache {

    static final GHPullRequest UNKNOWN = new GHPullRequest(new GHUser("UNKNOWN", "", ""), "");

    private static final Logger logger = LoggerFactory.getLogger(PullRequestCache.class);

    private final LoadingCache<String, GHPullRequest> cache;

    public PullRequestCache(final GitHubApiFacade facade) {
        this.cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<>() {
                    @Override
                    public GHPullRequest load(String key) {
                        return facade.getPullRequest(key);
                    }
                });
    }

    public GHPullRequest get(String url) {
        try {
            return cache.get(url);
        } catch (Exception e) {
            logger.error("exception while getting pull request from cache, for URL: " + url, e);
            return UNKNOWN;
        }
    }

    public void putAll(Collection<GHPullRequest> prs) {
        cache.putAll(prs.stream().collect(Collectors.toMap(GHPullRequest::getUrl, Function.identity())));
    }
}
