package com.tzachz.github.commentcounter;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:46
 */
public class OrganizationCommentCounter  {

    private final GitHubOrgRepos repos;
    private final GitHubCommentCounter commentCounter;

    protected OrganizationCommentCounter(String username, String password) {
        repos = new GitHubOrgRepos(username, password);
        commentCounter = new GitHubCommentCounter(username, password);
    }

    public void printCommentsLeaderBoard(String organization) {
        Set<String> repoNames = repos.getRepoNames(organization);
        UserComments total = new UserComments();
        for (String name : repoNames) {
            total.addAll(commentCounter.getUserComments(organization, name));
        }
        System.out.println(total.getLeaderBoard().toString());
    }
}
