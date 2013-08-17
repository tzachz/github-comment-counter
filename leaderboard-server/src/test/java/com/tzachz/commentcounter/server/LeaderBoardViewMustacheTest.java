package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.yammer.dropwizard.views.mustache.MustacheViewRenderer;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 20:04
 */
public class LeaderBoardViewMustacheTest {

    @Test
    public void mustacheRendersOrgNameIntoHeadline() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), "org1", true);
        String result = render(view);
        assertThat(result, containsString("<h1>GitHub Reviewers Leader Board: <a href=\"https://github.com/org1"));
    }

    @Test
    public void stillLoadingSaysStillLoading() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), "org1", false);
        String result = render(view);
        assertThat(result, containsString("Still Loading! Please wait while we fetch your organization's data from GitHub..."));
        assertThat(result, not(containsString("No comments! Start reviewing...")));
    }

    @Test
    public void loadedEmptyRecordsListSaysNoComments() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), "org1", true);
        String result = render(view);
        assertThat(result, containsString("No comments! Start reviewing..."));
    }

    @Test
    public void existingLoadedRecordsRenderedWithLink() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(new GHComment("user1", "some-url", "body"));
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), "org1", true);
        String result = render(view);
        assertThat(result, containsString("1 comments by "));
        assertThat(result, containsString("https://github.com/user1"));
    }

    @Test
    public void sampleCommentDisplayed() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(new GHComment("user1", "some-url", "a very intelligent comment indeed"));
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), "org1", true);
        String result = render(view);
        assertThat(result, containsString("a very intelligent comment indeed"));
    }

    private String render(LeaderBoardView view) throws IOException {
        MustacheViewRenderer renderer = new MustacheViewRenderer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.render(view, Locale.getDefault(), outputStream);
        return outputStream.toString();
    }
}
