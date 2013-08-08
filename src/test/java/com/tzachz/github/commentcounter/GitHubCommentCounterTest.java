package com.tzachz.github.commentcounter;

import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class GitHubCommentCounterTest {

    @Test
    public void getPublicUserEvents() throws Exception {
        GitHubCommentCounter gitHubCommentCounter = GitHubCommentCounter.connect("tzachz", "");
        UserComments comments = gitHubCommentCounter.getUserComments("tzachz");
        assertThat(comments.getCount(), greaterThan(0));
    }
}
