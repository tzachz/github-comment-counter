package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHOrg;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 14:10
 */
class OrgResource extends GitHubResource {

    OrgResource(Credentials credentials, String url) {
        super(credentials, url);
    }

    GHOrg getOrg(String organization) {
        return getResource()
                .path("orgs")
                .path(organization)
                .get(GHOrg.class);
    }
}
