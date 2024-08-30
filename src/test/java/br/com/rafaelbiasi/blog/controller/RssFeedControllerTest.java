package br.com.rafaelbiasi.blog.controller;

import br.com.rafaelbiasi.blog.data.AccountData;
import br.com.rafaelbiasi.blog.data.PostData;
import br.com.rafaelbiasi.blog.facade.PostFacade;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private List<PostData> createPosts(int size, LocalDateTime date) {
        List<PostData> posts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            posts.add(PostData.builder()
                    .code("code" + i)
                    .title("title" + i)
                    .body("body" + i)
                    .modified(date)
                    .author(AccountData.builder()
                            .firstName("first" + i)
                            .lastName("last" + i)
                            .build())
                    .build());
        }
        return posts;
    }

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
        when(postFacade.findAll(pageable)).thenReturn(page);
        when(request.getRequestURI()).thenReturn(url.toString());
        when(request.getServerName()).thenReturn(url.getHost());
        when(request.getServerPort()).thenReturn(url.getPort());
        when(request.getScheme()).thenReturn(url.getScheme());
        //WHEN
        Channel channel = rssFeedController.rssFeed(request);
        //THEN
        assertEquals("Spring Boot Blog Application", channel.getTitle());
        assertEquals("Spring Boot Blog Application", channel.getDescription());
        assertEquals("https://127.0.0.1", channel.getLink());
        assertEquals("https://127.0.0.1", channel.getUri());
        assertEquals("Rome Tools", channel.getGenerator());
        assertEquals(1, channel.getPubDate().compareTo(anotherDate));
        Item item1 = channel.getItems().getFirst();
        assertEquals("first0 last0", item1.getAuthor());
        assertEquals("https://127.0.0.1/post/code0", item1.getLink());
        assertEquals("title0", item1.getTitle());
        assertEquals("body0", item1.getDescription().getValue());
        assertEquals(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), item1.getPubDate());
        Item item2 = channel.getItems().get(1);
        assertEquals("first1 last1", item2.getAuthor());
        assertEquals("https://127.0.0.1/post/code1", item2.getLink());
        assertEquals("title1", item2.getTitle());
        assertEquals("body1", item2.getDescription().getValue());
        assertEquals(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), item2.getPubDate());
        verify(postFacade).findAll(any());
        verify(request).getRequestURI();
        verify(request).getServerName();
        verify(request).getServerPort();
        verify(request).getScheme();
    }

    @Test
    void rssFeedException() {
        //GIVEN
        when(postFacade.findAll(any())).thenThrow(new RuntimeException());
        //WHEN
        Executable executable = () -> rssFeedController.rssFeed(request);
        //GIVEN
        assertThrows(RuntimeException.class, executable);
        verify(postFacade).findAll(any());
    }

    //@Test
    //void template() {
    //    //GIVEN
    //    //WHEN
    //    //THEN
    //}
}