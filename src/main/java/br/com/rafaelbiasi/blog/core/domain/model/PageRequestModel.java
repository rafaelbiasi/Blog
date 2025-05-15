package br.com.rafaelbiasi.blog.core.domain.model;

public record PageRequestModel(int pageNumber, int pageSize) {

	public static PageRequestModel of(int pageNumber, int pageSize) {
		return new PageRequestModel(pageNumber, pageSize);
	}

	public boolean isPaged() {
		return true;
	}


}
