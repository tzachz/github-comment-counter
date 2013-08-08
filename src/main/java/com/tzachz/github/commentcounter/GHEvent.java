package com.tzachz.github.commentcounter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GHEvent {

    private String type;
    private GHRepo repo;

    @SuppressWarnings("UnusedDeclaration")
    public GHEvent() {
    }

    public GHEvent(String repoName) {
        this.repo = new GHRepo(repoName);
    }

    public String getType() {
        return type;
    }

    public GHRepo getRepo() {
        return repo;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public class GHRepo {
        private String name;

        @SuppressWarnings("UnusedDeclaration")
        public GHRepo() {
        }

        public GHRepo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
