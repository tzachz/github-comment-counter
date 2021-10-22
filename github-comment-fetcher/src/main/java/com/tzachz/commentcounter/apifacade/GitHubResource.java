package com.tzachz.commentcounter.apifacade;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 08/08/13
 * Time: 23:55
 */
public abstract class GitHubResource {

    private static final Logger logger = LoggerFactory.getLogger(GitHubResource.class);

    private static final int MAX_PAGES = 500;

    private final Client client;
    private final String url;

    GitHubResource(Credentials credentials, String url) {
        this.url = url;
        this.client = Client.create();
        this.client.addFilter(new HTTPBasicAuthFilter(credentials.getUsername(), credentials.getPassword()));
    }

    WebResource getResource() {
        return client.resource(url);
    }

    protected <T> T get(String url, final Class<T> type) {
        logger.info(String.format("fetching URL: %s", url));
        return client.resource(url).get(type);
    }

    <T> void scanPages(WebResource resource, GenericType<List<T>> type, PageProcessor<T> processor) {
        int totalItems = 0;
        for (int page = 1; page <= MAX_PAGES; page++) {
            logger.debug("Reading page {} from {}", page, resource);
            List<T> events = resource.queryParam("page", Integer.toString(page)).get(type);
            totalItems += events.size();
            processor.process(events);
            if (events.isEmpty()) {
                break;
            }
        }
        logger.info("{} items read from {}", totalItems, resource);
    }

    protected interface PageProcessor<T> {
        void process(List<T> page);
    }
}
