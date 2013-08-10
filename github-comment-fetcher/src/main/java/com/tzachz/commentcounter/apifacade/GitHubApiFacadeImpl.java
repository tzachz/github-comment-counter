package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

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

    public GitHubApiFacadeImpl(String username, String password) {
        this.orgResource = new OrgResource(username, password);
        this.orgRepositoriesResource = new OrgRepositoriesResource(username, password);
        this.repoCommentsResource = new RepoCommentsResource(username, password);
    }

    @Override
    public Object getOrg(String orgName) {
        return orgResource.getOrg(orgName);
    }

    @Override
    public Set<String> getOrgRepoNames(String orgName) {
        return orgRepositoriesResource.getRepoNames(orgName);
    }

    @Override
    public Collection<GHComment> getRepoComments(String orgName, String repoName, Date since) {
        return repoCommentsResource.getUserComments(orgName, repoName, since);
    }
}
