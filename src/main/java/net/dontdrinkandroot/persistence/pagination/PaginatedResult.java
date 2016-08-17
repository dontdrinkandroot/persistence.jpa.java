package net.dontdrinkandroot.persistence.pagination;

import java.io.Serializable;
import java.util.List;


public class PaginatedResult<T> implements Serializable
{

	private final Pagination pagination;

	private final List<T> entries;


	public PaginatedResult(Pagination pagination, List<T> entries)
	{
		this.pagination = pagination;
		this.entries = entries;
	}

	public Pagination getPagination()
	{
		return this.pagination;
	}

	public List<T> getEntries()
	{
		return this.entries;
	}
}
