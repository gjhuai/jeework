package com.wcs.base.util;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 */
public class PaginationSupport<T> implements Serializable {

	private static final long serialVersionUID = -7315205992759496506L;

	public static final int PAGESIZE = 10;

	private int pageSize = PAGESIZE;

	private int totalCount;

	private int[] indexes = new int[0];

	private int startIndex = 0;

	private int lastStartIndex;
	
	private List<T> result = Lists.newArrayList();

	// For search filter.
	public PaginationSupport(final List<T> result, int totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setResult(result);
		setStartIndex(0);
	}

	public PaginationSupport(final List<T> result, int totalCount, int startIndex) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setResult(result);
		setStartIndex(startIndex);
	}

	public PaginationSupport(final List<T> result, int totalCount, int startIndex, int pageSize) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setResult(result);
		setStartIndex(startIndex);
	}
	
	public void setPageNumber(int pageNumber) {
		setStartIndex((pageNumber - 1) * pageSize);
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageCount() {

		int totalPage = totalCount / pageSize;
		totalPage += totalCount % pageSize == 0 ? 0 : 1;

		return totalPage;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;

			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new int[count];

			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0)
			this.startIndex = 0;
		else if (startIndex >= totalCount)
			this.startIndex = indexes[indexes.length - 1];
		else if (startIndex <= 0)
			this.startIndex = 0;
		else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	// Get page number
	public int getCurrentPageIndex() {
		return getStartIndex() / pageSize + 1;
	}

	public int getFirstPageIndex() {
		return 1;
	}

	public int getNextPageIndex() {
		if (getCurrentPageIndex() >= getLastPageIndex())
			return getLastPageIndex();
		else
			return getCurrentPageIndex() + 1;
	}

	public int getPreviousPageIndex() {
		if (getCurrentPageIndex() <= 1)
			return 1;
		else
			return getCurrentPageIndex() - 1;
	}

	public int getLastPageIndex() {
		return getPageCount();
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;

		if (nextIndex >= totalCount)
			return getStartIndex();
		else
			return nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;

		if (previousIndex <= 1)
			return -1;
		else
			return previousIndex;
	}

	public int getLastIndex() {
		if (indexes != null && indexes.length > 0) {
			lastStartIndex = indexes[indexes.length - 1];
		}
		return lastStartIndex;

	}
}
