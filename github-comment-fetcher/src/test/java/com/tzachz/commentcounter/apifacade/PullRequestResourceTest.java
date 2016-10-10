package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PullRequestResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    private PullRequestResource resource;

    @Before
    public void setUp() {
        resource = new PullRequestResource(credentials);
    }

    @Test
    public void pullRequestFetched() throws Exception {
        GHPullRequest result = resource.getPullRequest("https://api.github.com/repos/kenshoo/facterj/pulls/1");
        assertThat(result.getUser(), equalTo(new GHUser("AvihayTsayeg", "")));
    }

    @Test
    public void getPullRequestOn404() {
        GHPullRequest result = resource.getPullRequest("https://api.github.com/repos/rails/rails/pulls/1");
        assertThat(result, is(nullValue()));
    }
}
