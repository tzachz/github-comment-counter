package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

/**
 * Created by imarks on 3/14/2017.
 */
public class OrgResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void getLogins() throws Exception {
        OrgResource orgResource = new OrgResource(credentials,credentials.getURL());
        Set<GHUser> organizations = orgResource.getLogins();
        assertThat(organizations, hasItem(new GHUser(492, "IT-Platform", "https://github.agodadev.io/avatars/u/492?")));
    }

}