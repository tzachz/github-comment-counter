package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHOrg;
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

    public GitHubApiFacadeImpl(Credentials credentials, String url) {
        this(new OrgResource(credentials, url),
                new OrgRepositoriesResource(credentials, url),
                new RepoCommentsResource(credentials, url),
                new PullRequestResource(credentials, url),
                new EmojisResource(credentials, url));
    }

    private GitHubApiFacadeImpl(OrgResource orgResource, OrgRepositoriesResource orgRepositoriesResource, RepoCommentsResource repoCommentsResource, PullRequestResource pullRequestResource, EmojisResource emojisResource) {
        this.orgResource = orgResource;
        this.orgRepositoriesResource = orgRepositoriesResource;
        this.repoCommentsResource = repoCommentsResource;
        this.pullRequestResource = pullRequestResource;
        this.emojisResource = emojisResource;
    }

    @Override
    public GHOrg getOrg(String orgName) {
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
    public Collection<GHPullRequest> getPullRequests(String orgName, String repoName, Date since) {
        return pullRequestResource.getPullRequests(orgName, repoName, since);
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
