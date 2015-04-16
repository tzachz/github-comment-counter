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
public class OrgRepositoriesResource extends GitHubResource {

    public OrgRepositoriesResource(String username, String password) {
        super(username, password);
    }

    public OrgRepositoriesResource(String token) {
        super(token);
    }

    public Set<GHRepo> getRepos(String organization) {
        final Set<GHRepo> ghRepos = Sets.newHashSet();
        WebResource resource = getResource()
                .path("orgs")
                .path(organization)
                .path("repos");
        scanPages(resource, new GenericType<List<GHRepo>>() {}, new PageProcessor<GHRepo>() {
                    @Override
                    public void process(List<GHRepo> page) {
                        ghRepos.addAll(page);
                    }
                }
        );
        return ghRepos;
    }
}
