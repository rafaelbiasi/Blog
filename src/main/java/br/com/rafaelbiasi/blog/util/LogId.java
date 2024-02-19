package br.com.rafaelbiasi.blog.util;

import com.github.f4b6a3.uuid.UuidCreator;

public interface LogId {

    static String logId() {
        return UuidCreator.getTimeOrderedEpoch().toString().toUpperCase();
    }
}
