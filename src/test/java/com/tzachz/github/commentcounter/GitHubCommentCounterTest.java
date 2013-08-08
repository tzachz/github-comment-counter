package com.tzachz.github.commentcounter;

import org.junit.Rule;
import org.junit.Test;

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
    public void atLeastOneCommentByMeOnThisRepo() throws Exception {
        GitHubCommentCounter gitHubCommentCounter = new GitHubCommentCounter(credentials.getUsername(), credentials.getPassword());
        UserComments comments = gitHubCommentCounter.getUserComments("tzachz", "github-comment-counter");
        assertThat(comments.getCommentCount("tzachz"), greaterThan(0));
    }
}
