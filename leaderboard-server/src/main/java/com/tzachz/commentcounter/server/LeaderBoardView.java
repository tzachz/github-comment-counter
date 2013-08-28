package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.yammer.dropwizard.views.View;

import java.util.List;
import java.util.Random;

import static com.google.common.collect.Lists.transform;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 19:13
 */
@SuppressWarnings("UnusedDeclaration")
public class LeaderBoardView extends View {

    private final static Random random = new Random();

    private final List<LeaderBoardRecord> records;
    private final String orgName;
    private final boolean loaded;
    private final String period;

    protected LeaderBoardView(List<Commenter> commenters, String orgName, boolean loaded, String period) {
        super("leaderboard.mustache");
        this.orgName = orgName;
        this.loaded = loaded;
        this.records = transform(commenters, new Function<Commenter, LeaderBoardRecord>() {
            @Override
            public LeaderBoardRecord apply(Commenter input) {
                List<GHComment> comments = input.getComments();
                GHComment randomComment = comments.get(random.nextInt(comments.size()));
                String avatarUrl = randomComment.getUser().getAvatarUrl();
                return new LeaderBoardRecord(input.getUsername(), comments.size(), randomComment.getBody(), randomComment.getHtmlUrl(), avatarUrl);
            }
        });
        this.period = period;
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

    public String getPeriod() {
        return period;
    }
}
