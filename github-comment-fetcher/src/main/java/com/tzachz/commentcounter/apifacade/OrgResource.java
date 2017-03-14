package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:10
 */
public class OrgResource extends GitHubResource {

    public OrgResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    public Object getOrg(String organization) {
        return getResource()
                .path("orgs")
                .path(organization)
                .get(Object.class);
    }

    public Set<GHUser> getLogins() {
        Set<GHUser> ghLogins = new HashSet<>();
        WebResource resource = getResource()
                .path("organizations");
        GenericType<List<GHUser>> genericType = new GenericType<List<GHUser>>() {
        };
        List<GHUser> events = new ArrayList<>();
        do {
            int lastId = events.isEmpty() ? 0 : events.get(events.size()-1).getId();
            events = resource.queryParam("since", Integer.toString(lastId)).get(genericType);
            ghLogins.addAll(events);
        } while (!events.isEmpty());
        return ghLogins;
    }

}
