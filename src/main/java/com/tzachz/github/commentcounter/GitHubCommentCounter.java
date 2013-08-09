package com.tzachz.github.commentcounter;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.github.commentcounter.api.GHComment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class GitHubCommentCounter extends GitHubClient {

    private static final DateFormat SINCE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public GitHubCommentCounter(String username, String password) {
        super(username, password);
    }

    public UserComments getUserComments(String organization, String repoName, Date since) {
        final UserComments userComments = new UserComments();
        WebResource resource = getResource()
                .path("repos")
                .path(organization)
                .path(repoName)
                .path("pulls")
                .path("comments")
                .queryParam("since", SINCE_FORMAT.format(since));
        scanPages(resource, new GenericType<List<GHComment>>() {}, new PageProcessor<GHComment>() {
            @Override
            public void process(List<GHComment> page) {
                userComments.addAll(page);
            }
        });
        return userComments;
    }

}

