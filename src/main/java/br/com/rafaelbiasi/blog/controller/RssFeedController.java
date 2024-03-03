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

@Slf4j
@RestController
@RequiredArgsConstructor
public class RssFeedController {

    private final PostFacade postFacade;

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
            List<Item> items = postFacade.findAll(pageable).stream()
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
        channel.setDescription("Spring Boot Blog Application");
        channel.setLink(baseUrl);
        channel.setUri(baseUrl);
        channel.setGenerator("Rome Tools");
        channel.setPubDate(new Date());
        log.debug("#{}={}. RSS Channel created with title: {}", "LogID", logId, channel.getTitle());
        return channel;
    }

    private Item createItem(String logId, PostData post, String baseUrl) {
        log.debug("#{}={}. Creating RSS item for post: {}", "LogID", logId, post.getTitle());
        Item item = new Item();
        item.setAuthor(post.getAuthor().getName());
        item.setLink(baseUrl + "/post/" + post.getCode());
        item.setTitle(post.getTitle());
        item.setPubDate(Date.from(post.getModified().atZone(ZoneId.systemDefault()).toInstant()));
        Description description = new Description();
        description.setValue(post.getBody());
        item.setDescription(description);
        log.debug("#{}={}. RSS item created for post: {}", "LogID", logId, post.getTitle());
        return item;
    }
}
