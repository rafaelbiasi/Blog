package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RssFeedControllerTest {

    private RssFeedController rssFeedController;
    @Mock
    private PostFacade postFacade;
    @Mock
    private HttpServletRequest request;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        rssFeedController = new RssFeedController(postFacade);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void rssFeed() throws URISyntaxException {
        //GIVEN
        int size = 2;
        LocalDateTime date = LocalDateTime.now();
        Date anotherDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        List<PostData> posts = createPosts(size, date);
        Pageable pageable = PageRequest.of(0, 20);
        Page<PostData> page = new PageImpl<>(posts, pageable, size);
        URI url = new URI("https://127.0.0.1/rss");
        when(postFacade.getAll(any())).thenReturn(page);
        when(request.getRequestURI()).thenReturn(url.toString());
        when(request.getServerName()).thenReturn(url.getHost());
        when(request.getServerPort()).thenReturn(url.getPort());
        when(request.getScheme()).thenReturn(url.getScheme());
        //WHEN
        Channel channel = rssFeedController.rssFeed(request);
        //THEN
        Assertions.assertEquals("Spring Boot Blog Application", channel.getTitle());
        Assertions.assertEquals("Spring Boot Blog Application", channel.getDescription());
        Assertions.assertEquals("https://127.0.0.1", channel.getLink());
        Assertions.assertEquals("https://127.0.0.1", channel.getUri());
        Assertions.assertEquals("Rome Tools", channel.getGenerator());
        Assertions.assertEquals(1, channel.getPubDate().compareTo(anotherDate));
        Item item1 = channel.getItems().get(0);
        Assertions.assertEquals("first0 last0", item1.getAuthor());
        Assertions.assertEquals("https://127.0.0.1/posts/code0", item1.getLink());
        Assertions.assertEquals("title0", item1.getTitle());
        Assertions.assertEquals("body0", item1.getDescription().getValue());
        Assertions.assertEquals(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), item1.getPubDate());
        Item item2 = channel.getItems().get(1);
        Assertions.assertEquals("first1 last1", item2.getAuthor());
        Assertions.assertEquals("https://127.0.0.1/posts/code1", item2.getLink());
        Assertions.assertEquals("title1", item2.getTitle());
        Assertions.assertEquals("body1", item2.getDescription().getValue());
        Assertions.assertEquals(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), item2.getPubDate());
        verify(postFacade).getAll(any());
        verify(request).getRequestURI();
        verify(request).getServerName();
        verify(request).getServerPort();
        verify(request).getScheme();
    }

    private List<PostData> createPosts(int size, LocalDateTime date) {
        List<PostData> posts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            posts.add(PostData.builder()
                    .code("code" + i)
                    .title("title" + i)
                    .body("body" + i)
                    .modified(date)
                    .account(AccountData.builder()
                            .firstName("first" + i)
                            .lastName("last" + i)
                            .build())
                    .build());
        }
        return posts;
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}