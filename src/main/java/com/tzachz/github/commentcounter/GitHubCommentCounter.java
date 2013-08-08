package com.tzachz.github.commentcounter;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Collections2.filter;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class GitHubCommentCounter {

    private static final String GITHUB_URL = "https://api.github.com";

    private static final Set<String> COUNTED_EVENT_TYPES = Sets.newHashSet("PullRequestReviewCommentEvent", "IssueCommentEvent");

    private Client client;

    public static GitHubCommentCounter connectAnonymously() {
        return new GitHubCommentCounter(Client.create());
    }

    public static GitHubCommentCounter connect(String username, String password) {
        Client c = Client.create();
        c.addFilter(new HTTPBasicAuthFilter(username, password));
        return new GitHubCommentCounter(c);
    }
    private GitHubCommentCounter(Client client) {
        this.client = client;
    }

    public UserComments getUserComments(String username) {
        UserComments userComments = new UserComments();
        WebResource resource = client.resource(GITHUB_URL).path("users").path(username).path("events");
        int page = 1;
        while (true) {
            System.out.println("Reading page " + page + " for user " + username +"; Counted " + userComments.getCount() + " comments so far...");
            List<GHEvent> ghEvents = resource.queryParam("page", Integer.toString(page)).get(new GenericType<List<GHEvent>>() {});
            Collection<GHEvent> countedEvents = filter(ghEvents, new Predicate<GHEvent>() {
                @Override
                public boolean apply(GHEvent input) {
                    return COUNTED_EVENT_TYPES.contains(input.getType());
                }
            });
            userComments.addAll(countedEvents);
            if (ghEvents.isEmpty()) {
                break;
            }
            page++;
        }
        return userComments;
    }
}

