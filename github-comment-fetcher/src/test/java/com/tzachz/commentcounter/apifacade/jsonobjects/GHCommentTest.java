package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class GHCommentTest {
    @Test
    public void setIssueUrlSetsPullRequestUrl() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\n" +
            "  \"issue_url\": \"https://api.github.com/repos/rails/rails/issues/24774\"\n" +
            "}";
        GHComment comment = mapper.readValue(json, GHComment.class);
        assertThat(
            comment.getPullRequestUrl(),
            equalTo("https://api.github.com/repos/rails/rails/pulls/24774")
        );
    }
}
