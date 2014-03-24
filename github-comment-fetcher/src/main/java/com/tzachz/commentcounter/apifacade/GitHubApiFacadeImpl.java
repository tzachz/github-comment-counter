package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:44
 */
public class GitHubApiFacadeImpl implements GitHubApiFacade {

    private final OrgResource orgResource;
    private final OrgRepositoriesResource orgRepositoriesResource;
    private final RepoCommentsResource repoCommentsResource;
    private final PullRequestResource pullRequestResource;
    private final EmojisResource emojisResource;

    public GitHubApiFacadeImpl(String username, String password) {
        this(new OrgResource(username, password),
                new OrgRepositoriesResource(username, password),
                new RepoCommentsResource(username, password),
                new PullRequestResource(username, password),
                new EmojisResource(username, password));
    }

    GitHubApiFacadeImpl(OrgResource orgResource, OrgRepositoriesResource orgRepositoriesResource, RepoCommentsResource repoCommentsResource, PullRequestResource pullRequestResource, EmojisResource emojisResource) {
        this.orgResource = orgResource;
        this.orgRepositoriesResource = orgRepositoriesResource;
        this.repoCommentsResource = repoCommentsResource;
        this.pullRequestResource = pullRequestResource;
        this.emojisResource = emojisResource;
    }

    @Override
    public Object getOrg(String orgName) {
        return orgResource.getOrg(orgName);
    }

    @Override
    public Set<GHRepo> getOrgRepos(String orgName) {
        return orgRepositoriesResource.getRepos(orgName);
    }

    @Override
    public Collection<GHComment> getRepoComments(String orgName, String repoName, Date since) {
        return repoCommentsResource.getUserComments(orgName, repoName, since);
    }

    @Override
    public GHPullRequest getPullRequest(String url) {
        return pullRequestResource.getPullRequest(url);
    }

    @Override
    public EmojisMap getEmojiMap() {
        return emojisResource.getEmojisMap();
    }

}
