package br.com.rafaelbiasi.blog.infrastructure.util;

import com.github.f4b6a3.ulid.UlidCreator;
import org.slf4j.MDC;

public interface LogId {

	String LOG_ID = "logId";

	static void startLogId() {
		MDC.put(LOG_ID, UlidCreator.getUlid().toString().toUpperCase());
	}

	static void endLogId() {
		MDC.remove(LOG_ID);
	}
}
