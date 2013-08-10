package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Commenter;
import com.yammer.dropwizard.views.View;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 19:13
 */
public class LeaderBoardView extends View {

    private final List<Commenter> board;

    protected LeaderBoardView(List<Commenter> board) {
        super("leaderboard.mustache");
        this.board = board;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<Commenter> getBoard() {
        return board;
    }
}
