package com.tzachz.commentcounter.apifacade;

import com.google.common.collect.Sets;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:58
 */
class OrgRepositoriesResource extends GitHubResource {

    OrgRepositoriesResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    Set<GHRepo> getRepos(String organization) {
        final Set<GHRepo> ghRepos = Sets.newHashSet();
        final WebResource resource = getResource()
                .path("orgs")
                .path(organization)
                .path("repos");
        scanPages(resource, new GenericType<List<GHRepo>>() {}, ghRepos::addAll);
        return ghRepos;
    }
}
