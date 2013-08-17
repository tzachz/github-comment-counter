package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.tzachz.commentcounter.Commenter;
import com.yammer.dropwizard.views.View;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 19:13
 */
public class LeaderBoardView extends View {

    private final List<LeaderBoardRecord> records;

    protected LeaderBoardView(List<Commenter> commenters) {
        super("leaderboard.mustache");
        records = transform(commenters, new Function<Commenter, LeaderBoardRecord>() {
            @Override
            public LeaderBoardRecord apply(Commenter input) {
                return new LeaderBoardRecord(input.getUsername(), input.getComments().size());
            }
        });
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<LeaderBoardRecord> getRecords() {
        return records;
    }

}
