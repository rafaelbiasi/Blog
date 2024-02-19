package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import br.com.rafaelbiasi.blog.util.LogId;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for generating an RSS feed for the blog posts.
 * This controller uses the {@link PostFacade} to fetch all blog posts and formats them as an RSS feed
 * using Rome Tools, a library for working with RSS and Atom feeds. The feed includes basic metadata for each post,
 * such as title, link, author, and publication date.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RssFeedController {

    private final PostFacade postFacade;

    /**
     * Generates an RSS feed for all blog posts.
     *
     * @param request the {@link HttpServletRequest} used to construct base URLs for the feed and posts
     * @return a {@link Channel} object representing the RSS feed, including metadata and items for each post
     */
    @GetMapping(path = "/rss")
    public Channel rssFeed(HttpServletRequest request) {
        try {
            String logId = LogId.logId();
            log.info("#{}={}. Accessing RSS feed", "LogID", logId);
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();

            Channel channel = createChannel(logId, baseUrl);
            Pageable pageable = PageRequest.of(0, 20);
            List<Item> items = postFacade.getAll(pageable).stream()
                    .map(post -> createItem(logId, post, baseUrl))
                    .collect(Collectors.toList());

            log.info("#{}={}. Number of RSS items created: {}", "LogID", logId, items.size());
            channel.setItems(items);

            return channel;
        } catch (Exception e) {
            log.error("Error generating RSS feed", e);
            throw e;
        }
    }

    private Channel createChannel(String logId, String baseUrl) {
        Channel channel = new Channel("rss_2.0");
        channel.setTitle("Spring Boot Blog Application");
        channel.setDescription("My Spring Boot Blog Demo App");
        channel.setLink(baseUrl);
        channel.setUri(baseUrl);
        channel.setGenerator("Custom Sauce");
        channel.setPubDate(new Date());
        log.debug("#{}={}. RSS Channel created with title: {}", "LogID", logId, channel.getTitle());
        return channel;
    }

    private Item createItem(String logId, PostData post, String baseUrl) {
        log.debug("#{}={}. Creating RSS item for post: {}", "LogID", logId, post.getTitle());
        Item item = new Item();
        item.setAuthor(post.getAccount().getName());
        item.setLink(baseUrl + "/posts/" + post.getCode());
        item.setTitle(post.getTitle());
        item.setPubDate(Date.from(post.getModified().atZone(ZoneId.systemDefault()).toInstant()));
        Description description = new Description();
        description.setValue(post.getBody());
        item.setDescription(description);
        log.debug("#{}={}. RSS item created for post: {}", "LogID", logId, post.getTitle());
        return item;
    }
}
