package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHOrg;

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

    public GHOrg getOrg(String organization) {
        return getResource()
                .path("orgs")
                .path(organization)
                .get(GHOrg.class);
    }
}
