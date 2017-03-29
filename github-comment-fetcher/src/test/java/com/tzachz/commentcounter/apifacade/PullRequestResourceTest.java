package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PullRequestResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void pullRequestFetched() throws Exception {
        PullRequestResource resource = new PullRequestResource(credentials, credentials.getURL());
        GHPullRequest result = resource.getPullRequest("https://api.github.com/repos/kenshoo/facterj/pulls/1");
        assertThat(result.getUser(), equalTo(new GHUser("AvihayTsayeg", "", "")));
    }

}
