package com.tzachz.commentcounter.apifacade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tzachz on 6/5/15
 */
public class GitHubTargetConfiguration {

    public GitHubTargetConfiguration() {}

    @JsonIgnore
    public GitHubTargetConfiguration(String organization, String includeRepoPattern) {
        this.organization = organization;
        this.includeRepoPattern = includeRepoPattern;
    }

    @JsonProperty
    private String organization;

    @JsonProperty
    private String includeRepoPattern = ".*";

    public String getOrganization() {
        return organization;
    }

    public String getIncludeRepoPattern() {
        return includeRepoPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitHubTargetConfiguration that = (GitHubTargetConfiguration) o;

        if (includeRepoPattern != null ? !includeRepoPattern.equals(that.includeRepoPattern) : that.includeRepoPattern != null)
            return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = organization != null ? organization.hashCode() : 0;
        result = 31 * result + (includeRepoPattern != null ? includeRepoPattern.hashCode() : 0);
        return result;
    }
}
