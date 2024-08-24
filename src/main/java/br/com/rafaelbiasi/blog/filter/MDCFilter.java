package br.com.rafaelbiasi.blog.filter;

import br.com.rafaelbiasi.blog.util.LogId;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MDCFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            LogId.mdcLogId();
            chain.doFilter(request, response);
        } finally {
            LogId.removeMdcLogId();
        }
    }
}
