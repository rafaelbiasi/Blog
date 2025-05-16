package br.com.rafaelbiasi.blog.core.domain.model;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

public record PageModel<T>(List<T> content, PageRequestModel pageRequest, long total) {

	public static <T> PageModel<T> of(List<T> content, PageRequestModel pageable, long totalElements) {
		return new PageModel<>(content, pageable, totalElements);
	}

	public long number() {
		return pageRequest.isPaged() ? pageRequest.pageNumber() : 0;
	}

	public int totalPages() {
		return size() == 0 ? 1 : (int) Math.ceil((double) total / (double) size());
	}

	public int size() {
		return pageRequest.isPaged() ? pageRequest.pageSize() : content.size();
	}

	public <U> PageModel<U> map(Function<? super T, ? extends U> converter) {
		return PageModel.of(getConvertedContent(converter), pageable(), total);
	}

	public PageRequestModel pageable() {
		return pageRequest;
	}

	public Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	public Spliterator<T> spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), 0);
	}

	public Iterator<T> iterator() {
		return content.iterator();
	}

	private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
		requireNonNull(converter, "Function must not be null");

		return this.stream().map(converter).collect(Collectors.toList());
	}
}
