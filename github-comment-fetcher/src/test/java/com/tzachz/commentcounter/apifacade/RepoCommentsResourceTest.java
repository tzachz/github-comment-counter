package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class RepoCommentsResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    private RepoCommentsResource repoCommentsResource;

    @Before
    public void setUp() {
        repoCommentsResource = new RepoCommentsResource(credentials);
    }

    @Test
    public void containsPRDiscussionComments() throws Exception {
        Collection<GHComment> comments = repoCommentsResource.getUserComments("kenshoo", "facterj", new Date(0l));
        String htmlUrl = "https://github.com/kenshoo/facterj/pull/1#discussion_r2192722";
        assertThat(comments, hasItem(hasProperty("htmlUrl", is(htmlUrl))));
    }

    @Test
    public void containsIssueComments() {
        Collection<GHComment> comments = repoCommentsResource.getUserComments("kenshoo", "facterj", new Date(0l));
        String htmlUrl = "https://github.com/kenshoo/facterj/pull/1#issuecomment-10593978";
        assertThat(comments, hasItem(hasProperty("htmlUrl", is(htmlUrl))));
    }
}
