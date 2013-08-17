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
@SuppressWarnings("UnusedDeclaration")
public class LeaderBoardView extends View {

    private final List<LeaderBoardRecord> records;
    private final String orgName;
    private final boolean loaded;

    protected LeaderBoardView(List<Commenter> commenters, String orgName, boolean loaded) {
        super("leaderboard.mustache");
        this.orgName = orgName;
        this.loaded = loaded;
        this.records = transform(commenters, new Function<Commenter, LeaderBoardRecord>() {
            @Override
            public LeaderBoardRecord apply(Commenter input) {
                return new LeaderBoardRecord(input.getUsername(), input.getComments().size());
            }
        });
    }

    public List<LeaderBoardRecord> getRecords() {
        return records;
    }

    public String getOrgName() {
        return orgName;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
