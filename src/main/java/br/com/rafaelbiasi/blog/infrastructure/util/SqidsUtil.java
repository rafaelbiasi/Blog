package br.com.rafaelbiasi.blog.infrastructure.util;

import org.sqids.Sqids;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

public class SqidsUtil {

	private static final Sqids SQIDS = Sqids.builder().build();

	public static String encodeId(Long id) {
		return ofNullable(id)
				.map(idLong -> SQIDS.encode(singletonList(idLong)))
				.orElse(null);
	}

	public static Long decodeId(String code) {
		return ofNullable(code)
				.filter(not(String::isBlank))
				.map(SqidsUtil::decode)
				.orElse(null);
	}

	private static Long decode(String codeStr) {
		return SQIDS.decode(codeStr).getFirst();
	}
}
