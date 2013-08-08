package com.tzachz.github.commentcounter;

import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:58
 */
public class GitHubOrgReposTest {

    @Rule
    public GitHubCredentialsRule credentials = new GitHubCredentialsRule();

    @Test
    public void facterjRepoIsInKenshooReposList() throws Exception {
        GitHubOrgRepos repos = new GitHubOrgRepos(credentials.getUsername(), credentials.getPassword());
        Set<String> repoNames = repos.getRepoNames("kenshoo");
        assertThat(repoNames, hasItem("facterj"));
    }
}
