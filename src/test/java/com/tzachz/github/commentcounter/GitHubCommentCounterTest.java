package com.tzachz.github.commentcounter;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class GitHubCommentCounterTest {

    @Rule
    public GitHubCredentialsRule credentials = new GitHubCredentialsRule();

    @Test
    public void atLeastOneCommentByAvihayOnFacterJ() throws Exception {
        GitHubCommentCounter gitHubCommentCounter = new GitHubCommentCounter(credentials.getUsername(), credentials.getPassword());
        UserComments comments = gitHubCommentCounter.getUserComments("kenshoo", "facterj", new Date(0l));
        assertThat(comments.getCommentCount("AvihayTsayeg"), greaterThan(0));
    }
}
