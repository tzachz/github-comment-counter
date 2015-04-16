package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:58
 */
public class OrgRepositoriesResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void facterjRepoIsInKenshooReposList() throws Exception {
        OrgRepositoriesResource reposResource = null;
        if (credentials.isTokenBased()) {
            reposResource = new OrgRepositoriesResource(credentials.getToken());
        } else {
            reposResource = new OrgRepositoriesResource(credentials.getUsername(), credentials.getPassword());
        }
        Set<GHRepo> repos = reposResource.getRepos("kenshoo");
        assertThat(repos, hasItem(new GHRepo("facterj")));
    }
}
