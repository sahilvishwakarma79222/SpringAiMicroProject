package com.substring.quiz.category.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> {

	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private List<T> content;
	private boolean isLast;
	
}
