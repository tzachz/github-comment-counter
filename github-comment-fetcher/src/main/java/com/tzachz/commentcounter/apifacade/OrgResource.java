package com.tzachz.commentcounter.apifacade;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:10
 */
public class OrgResource extends GitHubResource {

    public OrgResource(String username, String password) {
        super(username, password);
    }

    public OrgResource(String token) {
        super(token);
    }

    public Object getOrg(String organization) {
        return getResource()
                .path("orgs")
                .path(organization)
                .get(Object.class);
    }
}
