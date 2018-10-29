package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
class RepoCommentsResource extends GitHubResource {

    private static final DateFormat SINCE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    RepoCommentsResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    Collection<GHComment> getUserComments(String organization, String repoName, Date since) {
        final Collection<GHComment> userComments = new ArrayList<>();
        final WebResource resource = getResource()
                .path("repos")
                .path(organization)
                .path(repoName)
                .path("pulls")
                .path("comments")
                .queryParam("since", SINCE_FORMAT.format(since));
        scanPages(resource, new GenericType<List<GHComment>>() {}, userComments::addAll);
        return userComments;
    }

}
