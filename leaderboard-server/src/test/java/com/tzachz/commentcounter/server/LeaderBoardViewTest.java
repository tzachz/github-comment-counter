package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.yammer.dropwizard.views.mustache.MustacheViewRenderer;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 18:36
 */
public class LeaderBoardViewTest {

    @Test
    public void renderWithMustache() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(new GHComment("user1", "some-url"));
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter));
        MustacheViewRenderer renderer = new MustacheViewRenderer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.render(view, Locale.getDefault(), outputStream);
        String result = outputStream.toString();
        assertThat(result, containsString("<b>1</b> comments by "));
        assertThat(result, containsString("https://github.com/user1"));
    }
}
