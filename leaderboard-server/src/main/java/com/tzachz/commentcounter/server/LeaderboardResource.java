package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Commenter;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:57
 */
@Path("/leaderboard")
@Produces(MediaType.APPLICATION_JSON)
public class LeaderboardResource {

    @GET
    @Timed
    public List<Commenter> getLeaderboard() {
        // TODO - the good stuff goes here
        return Arrays.asList(new Commenter("user1", 3), new Commenter("user2", 2));
    }
}
