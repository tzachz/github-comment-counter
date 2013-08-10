package com.tzachz.commentcounter.apifacade;

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
        OrgRepositoriesResource repos = new OrgRepositoriesResource(credentials.getUsername(), credentials.getPassword());
        Set<String> repoNames = repos.getRepoNames("kenshoo");
        assertThat(repoNames, hasItem("facterj"));
    }
}
