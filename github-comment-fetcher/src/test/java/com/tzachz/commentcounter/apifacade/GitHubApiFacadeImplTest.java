package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 10:49
 */
public class GitHubApiFacadeImplTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void pullRequestFetched() throws Exception {
        GitHubApiFacadeImpl facade = new GitHubApiFacadeImpl(credentials.getUsername(), credentials.getPassword());
        GHPullRequest result = facade.getPullRequest("https://api.github.com/repos/kenshoo/facterj/pulls/1");
        assertThat(result.getUser(), equalTo(new GHUser("AvihayTsayeg")));
    }
}
