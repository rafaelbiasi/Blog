package br.com.rafaelbiasi.blog.core.vo;

public record SimplePageRequest(int pageNumber, int pageSize) {

	public static SimplePageRequest of(int pageNumber, int pageSize) {
		return new SimplePageRequest(pageNumber, pageSize);
	}

	public boolean isPaged() {
		return true;
	}


}
