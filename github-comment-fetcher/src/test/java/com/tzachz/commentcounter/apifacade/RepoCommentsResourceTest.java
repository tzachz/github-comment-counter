package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class RepoCommentsResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void atLeastOneCommentFacterJ() throws Exception {
        RepoCommentsResource repoCommentsResource = null;
        if (credentials.isTokenBased()) {
            repoCommentsResource = new RepoCommentsResource(credentials.getToken());
        } else {
            repoCommentsResource = new RepoCommentsResource(credentials.getUsername(), credentials.getPassword());
        }
        Collection<GHComment> comments = repoCommentsResource.getUserComments("kenshoo", "facterj", new Date(0l));
        assertThat(comments.size(), greaterThan(0));
    }
}
