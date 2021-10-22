package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.EmojisMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

public class EmojisCommentRendererTest {

    private final EmojisMap emojisMap = new EmojisMap(Map.of(
            "smile", "http://smile",
            "thumbsup", "http://thumbsup",
            "+1", "http://+1",
            "-1", "http://-1"
    ));

    @Test
    public void emojisReplacedWithHtmlImages() {
        String origin = "Great! :smile: !";
        String comment = new EmojisCommentRenderer(emojisMap).render(origin);
        assertThat(comment, is("Great! <img alt=\":smile:\" src=\"http://smile\" height=\"20\" width=\"20\" align=\"absmiddle\"> !"));
    }

    @Test
    public void replacesMultipleEmojis() {
        String origin = ":smile: :+1: yes :thumbsup:";
        String comment = new EmojisCommentRenderer(emojisMap).render(origin);
        assertThat(comment, stringContainsInOrder(List.of("alt=\":smile:\"", "alt=\":+1:\"", "alt=\":thumbsup:\"")));
    }
}