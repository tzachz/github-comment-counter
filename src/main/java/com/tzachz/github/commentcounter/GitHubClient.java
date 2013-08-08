package com.tzachz.github.commentcounter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:55
 */
public abstract class GitHubClient {

    private static final String GITHUB_URL = "https://api.github.com";

    private WebResource resource;

    protected GitHubClient(String username, String password) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        this.resource = client.resource(GITHUB_URL);
    }

    protected WebResource getResource() {
        return resource;
    }

    static protected <T> void scanPages(WebResource resource, int maxPages, GenericType<List<T>> type, PageProcessor<T> processor) {
        maxPages = maxPages<=0? Integer.MAX_VALUE : maxPages;
        int totalItems = 0;
        for (int page = 1; page <= maxPages; page++) {
            System.out.println(MessageFormat.format("{0}: reading page {1}...", resource.toString(), page));
            List<T> events = resource.queryParam("page", Integer.toString(page)).get(type);
            totalItems += events.size();
            if (events.isEmpty()) {
                break;
            } else {
                processor.process(events);
            }
        }
        System.out.println(MessageFormat.format("{0}: read {1} items.", resource.toString(), totalItems));
    }

    protected interface PageProcessor<T> {
        public void process(List<T> page);
    }
}
