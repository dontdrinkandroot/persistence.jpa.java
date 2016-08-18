package net.dontdrinkandroot.persistence.service;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import net.dontdrinkandroot.persistence.entity.Entity;
import net.dontdrinkandroot.persistence.pagination.PaginatedResult;


public interface EntityService<E extends Entity<I>, I>
{

	E find(I id);

	E save(E entity);

	List<E> listAll();

	List<E> listAll(long first, long count);

	List<E> listAll(long first, long count, SingularAttribute<? super E, ?> sortAttribute, boolean asc);

	void delete(E entity);

	PaginatedResult<E> listPaginated(int page, int perPage);

	PaginatedResult<E> listPaginated(int page, int perPage, SingularAttribute<? super E, ?> sortAttribute, boolean asc);

	long findCount();
}
