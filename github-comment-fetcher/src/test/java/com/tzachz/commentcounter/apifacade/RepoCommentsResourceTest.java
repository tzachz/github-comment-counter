package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.CommentsByUser;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
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
public class RepoCommentsResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void atLeastOneCommentByAvihayOnFacterJ() throws Exception {
        RepoCommentsResource repoCommentsResource =
                new RepoCommentsResource(credentials.getUsername(), credentials.getPassword());
        CommentsByUser comments = new CommentsByUser();
        comments.addAll(repoCommentsResource.getUserComments("kenshoo", "facterj", new Date(0l)), new GHRepo("facterj"));
        assertThat(comments.getCommentCount("AvihayTsayeg"), greaterThan(0));
    }
}
