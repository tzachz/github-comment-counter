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
    private static final int MAX_PAGES = 20;

    private WebResource resource;

    protected GitHubClient(String username, String password) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        this.resource = client.resource(GITHUB_URL);
    }

    protected WebResource getResource() {
        return resource;
    }

    static protected <T> void scanPages(WebResource resource, GenericType<List<T>> type, PageProcessor<T> processor) {
        int totalItems = 0;
        int pageSize = -1;
        for (int page = 1; page <= MAX_PAGES; page++) {
            System.out.println(MessageFormat.format("{0}: reading page {1}...", resource.toString(), page));
            List<T> events = resource.queryParam("page", Integer.toString(page)).get(type);
            totalItems += events.size();
            processor.process(events);
            if (events.isEmpty() || events.size() < pageSize) {
                break;
            } else {
                pageSize = events.size();
            }
        }
        System.out.println(MessageFormat.format("{0}: read {1} items.", resource.toString(), totalItems));
    }

    protected interface PageProcessor<T> {
        public void process(List<T> page);
    }
}
