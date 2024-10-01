package br.com.rafaelbiasi.blog.ui.filter;

import br.com.rafaelbiasi.blog.infrastructure.util.LogId;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MDCFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        try {
            LogId.startLogId();
            chain.doFilter(request, response);
        } finally {
            LogId.endLogId();
        }
    }
}
