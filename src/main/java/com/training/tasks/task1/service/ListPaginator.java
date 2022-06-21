package com.training.tasks.task1.service;

import java.util.Collections;
import java.util.List;

public class ListPaginator {

	public <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {

		if (pageSize <= 0 || page <= 0) {
			throw new IllegalArgumentException("invalid page number/size: " + pageSize);
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() < fromIndex) {
			return Collections.emptyList();
		}
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}

	public <T> int getMaxPageNumber(List<T> sourceList, int pageSize) {
		if (pageSize <= 0) {
			throw new IllegalArgumentException("invalid page size: " + pageSize);
		}
		return sourceList.size() / pageSize + 1;
	}
}
