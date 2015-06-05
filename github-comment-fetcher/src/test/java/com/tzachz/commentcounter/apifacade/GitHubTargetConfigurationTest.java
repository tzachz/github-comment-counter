package com.tzachz.commentcounter.apifacade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class GitHubTargetConfigurationTest {

    @Test
    public void toAndFromJson() throws Exception {
        final GitHubTargetConfiguration value = new GitHubTargetConfiguration("my-org", "^backend-.*");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(value);
        final GitHubTargetConfiguration deserialized = objectMapper.readValue(json, GitHubTargetConfiguration.class);
        assertThat(deserialized, equalTo(value));
    }

    @Test
    public void defaultPatternIsAllInclusive() throws Exception {
        final String defaultPattern = new GitHubTargetConfiguration().getIncludeRepoPattern();
        assertThat(defaultPattern, equalTo(".*"));
    }
}