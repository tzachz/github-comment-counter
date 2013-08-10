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

    private final static Logger logger = LoggerFactory.getLogger(GitHubResource.class);

    private static final String GITHUB_URL = "https://api.github.com";
    private static final int MAX_PAGES = 20;

    private WebResource resource;

    protected GitHubResource(String username, String password) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        this.resource = client.resource(GITHUB_URL);
    }

    protected WebResource getResource() {
        return resource;
    }

    protected <T> void scanPages(WebResource resource, GenericType<List<T>> type, PageProcessor<T> processor) {
        int totalItems = 0;
        int pageSize = -1;
        for (int page = 1; page <= MAX_PAGES; page++) {
            logger.debug("Reading page {} from {}", page, resource.toString());
            List<T> events = resource.queryParam("page", Integer.toString(page)).get(type);
            totalItems += events.size();
            processor.process(events);
            if (events.isEmpty() || events.size() < pageSize) {
                break;
            } else {
                pageSize = events.size();
            }
        }
        logger.info("{} items read from {}", totalItems, resource.toString());
    }

    protected interface PageProcessor<T> {
        public void process(List<T> page);
    }
}
