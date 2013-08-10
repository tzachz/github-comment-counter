package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Commenter;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:57
 */
@Path("/leaderboard")
@Produces(MediaType.APPLICATION_JSON)
public class LeaderBoardResource {

    private final LeaderBoardStore store;

    public LeaderBoardResource(LeaderBoardStore store) {
        this.store = store;
    }

    @GET
    @Timed
    public List<Commenter> getLeaderBoard() {
        return store.get();
    }
}
