package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
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

    private final GHUser owner = new GHUser(7, "kenshoo", "https://api.github.com/kenshoo/avatars/u/207?");

    @Test
    public void facterjRepoIsInKenshooReposList() throws Exception {
        OrgRepositoriesResource reposResource = new OrgRepositoriesResource(credentials, credentials.getURL());
        Set<GHRepo> repos = reposResource.getRepos("kenshoo");
        assertThat(repos, hasItem(new GHRepo("facterj", owner)));
    }
}
