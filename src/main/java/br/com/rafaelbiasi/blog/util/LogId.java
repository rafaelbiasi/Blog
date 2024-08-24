package br.com.rafaelbiasi.blog.util;

import com.github.f4b6a3.ulid.UlidCreator;
import org.slf4j.MDC;

public interface LogId {

    String LOG_ID = "logId";

    static void mdcLogId() {
        MDC.put(LOG_ID, UlidCreator.getUlid().toString().toUpperCase());
    }

    static void removeMdcLogId() {
        MDC.remove(LOG_ID);
    }
}
