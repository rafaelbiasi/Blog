package br.com.rafaelbiasi.blog.ui.controller;

import br.com.rafaelbiasi.blog.application.data.PostData;
import br.com.rafaelbiasi.blog.application.facade.PostFacade;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RssFeedController {

	private final PostFacade postFacade;

	@GetMapping(path = "/rss")
	public Channel rssFeed(final HttpServletRequest request) {
		log.info("Accessing RSS feed");
		val baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null)
				.build()
				.toUriString();
		val channel = createChannel(baseUrl);
		val pageable = PageRequest.of(0, 20);
		val items = postFacade.findAll(pageable).stream()
				.map(post -> createItem(post, baseUrl))
				.collect(Collectors.toList());
		log.info("Number of RSS items created: {}", items.size());
		channel.setItems(items);
		return channel;
	}

	private Channel createChannel(final String baseUrl) {
		val channel = new Channel("rss_2.0");
		channel.setTitle("Spring Boot Blog Application");
		channel.setDescription("Spring Boot Blog Application");
		channel.setLink(baseUrl);
		channel.setUri(baseUrl);
		channel.setGenerator("Rome Tools");
		channel.setPubDate(new Date());
		log.debug("RSS Channel created with title: {}", channel.getTitle());
		return channel;
	}

	private Item createItem(
			final PostData post,
			final String baseUrl
	) {
		log.debug("Creating RSS item for post: {}", post.getTitle());
		val item = new Item();
		item.setAuthor(post.getAuthor().getName());
		item.setLink(baseUrl + "/post/" + post.getCode());
		item.setTitle(post.getTitle());
		item.setPubDate(Date.from(post.getModified().atZone(ZoneId.systemDefault()).toInstant()));
		val description = new Description();
		description.setValue(post.getBody());
		item.setDescription(description);
		log.debug("RSS item created for post: {}", post.getTitle());
		return item;
	}
}
