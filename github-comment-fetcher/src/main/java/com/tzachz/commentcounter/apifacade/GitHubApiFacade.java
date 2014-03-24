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
 * Time: 14:35
 */
public interface GitHubApiFacade {

    public Object getOrg(String orgName);

    public Set<GHRepo> getOrgRepos(String orgName);

    public Collection<GHComment> getRepoComments(String orgName, String repoName, Date since);

    public GHPullRequest getPullRequest(String url);

    public EmojisMap getEmojiMap();
}
