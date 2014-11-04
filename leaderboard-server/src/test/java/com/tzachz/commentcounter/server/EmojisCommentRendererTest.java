package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableMap;
import com.tzachz.commentcounter.apifacade.EmojisMap;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmojisCommentRendererTest {

    private EmojisMap emojisMap = new EmojisMap(ImmutableMap.of("smile", "http://smile"));

    @Test
    public void emojisReplacedWithHtmlImages() throws Exception {
        String origin = "Great! :smile: !";
        String comment = new EmojisCommentRenderer(emojisMap).render(origin);
        assertThat(comment, is("Great! <img alt=\":smile:\" src=\"http://smile\" height=\"20\" width=\"20\" align=\"absmiddle\"> !"));
    }

}