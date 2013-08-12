package com.tzachz.commentcounter.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 13:57
 */
@Path("/leaderboard")
@Produces(MediaType.TEXT_HTML)
public class LeaderBoardResource {

    private final LeaderBoardStore store;

    public LeaderBoardResource(LeaderBoardStore store) {
        this.store = store;
    }

    @GET
    public LeaderBoardView getLeaderBoard() {
        return new LeaderBoardView(store.get());
    }

}
