package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.GHCommentBuilder;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
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

    private GHCommentBuilder commentBuilder = new GHCommentBuilder();
    private GHRepo repo = new GHRepo("my-repo");

    @Test
    public void mustacheRendersOrgNameIntoHeadline() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("<h1>GitHub Reviewers Leader Board: <a href=\"https://github.com/org1"));
    }

    @Test
    public void stillLoadingSaysStillLoading() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), transformer(), "org1", false, "today");
        String result = render(view);
        assertThat(result, containsString("Still Loading! Please wait while we fetch your organization's data from GitHub..."));
        assertThat(result, not(containsString("No comments! Start reviewing...")));
    }

    @Test
    public void loadedEmptyRecordsListSaysNoComments() throws Exception {
        LeaderBoardView view = new LeaderBoardView(Lists.<Commenter>newArrayList(), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("No comments! Start reviewing..."));
    }

    @Test
    public void existingLoadedRecordsRenderedWithLink() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url"), repo);
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("1 comments in 1 repos by "));
        assertThat(result, containsString("https://github.com/user1"));
    }

    @Test
    public void sampleCommentDisplayed() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "a very intelligent comment indeed"), repo);
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("a very intelligent comment indeed"));
    }

    @Test
    public void sampleCommentRepoNameInDiscussion() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "a very intelligent comment indeed"), repo);
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("[view discussion in my-repo]"));
    }

    @Test
    public void avatarImageDisplayed() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "a very intelligent comment indeed", "avatar-url"), repo);
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("<img src=\"avatar-url\" alt=\"user1\""));
    }

    @Test
    public void commentHtmlNotEscaped() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "contains <img>!", "avatar-url"), repo);
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), transformer(), "org1", true, "today");
        String result = render(view);
        assertThat(result, containsString("contains <img>!"));
    }

    private String render(LeaderBoardView view) throws IOException {
        MustacheViewRenderer renderer = new MustacheViewRenderer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.render(view, Locale.getDefault(), outputStream);
        return outputStream.toString();
    }

    private CommenterToRecordTransformer transformer() {
        return new CommenterToRecordTransformer(Lists.<CommentRenderer>newArrayList());
    }
}
