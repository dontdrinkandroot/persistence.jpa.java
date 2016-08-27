/**
 * Copyright (C) 2012-2016 Philip Washington Sorst <philip@sorst.net>
 * and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dontdrinkandroot.persistence.service;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.persistence.dao.EntityDao;
import net.dontdrinkandroot.persistence.entity.Entity;
import net.dontdrinkandroot.persistence.pagination.PaginatedResult;
import net.dontdrinkandroot.persistence.pagination.Pagination;


/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DaoEntityService<E extends Entity<I>, I> implements EntityService<E, I>
{

	private EntityDao<E, I> dao;


	protected DaoEntityService()
	{
		/* Reflection instantiation */
	}

	public DaoEntityService(EntityDao<E, I> dao)
	{
		this.dao = dao;
	}

	@Override
	@Transactional(readOnly = true)
	public E find(I id)
	{
		return this.getDao().find(id);
	}

	@Override
	@Transactional
	public E save(E entity)
	{
		E savedEntity = this.getDao().save(entity, true);
		return savedEntity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> listAll()
	{
		return this.getDao().findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public long findCount()
	{
		return this.getDao().getCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> listAll(long first, long count)
	{
		return this.getDao().findAll(null, true, (int) first, (int) count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> listAll(long first, long count, SingularAttribute<? super E, ?> sortAttribute, boolean asc)
	{
		return this.getDao().findAll(sortAttribute, asc, (int) first, (int) count);
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResult<E> listPaginated(int page, int perPage)
	{
		long count = this.findCount();
		List<E> results = this.getDao().findAll(null, true, (page - 1) * perPage, perPage);
		return new PaginatedResult<E>(new Pagination(page, perPage, (int) count), results);
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResult<E> listPaginated(
			int page,
			int perPage,
			SingularAttribute<? super E, ?> sortAttribute,
			boolean asc)
	{
		long count = this.getDao().getCount();
		List<E> results = this.getDao().findAll(sortAttribute, asc, (page - 1) * perPage, perPage);
		return new PaginatedResult<E>(new Pagination(page, perPage, (int) count), results);
	}

	@Override
	@Transactional
	public void delete(E entity)
	{
		this.getDao().delete(entity);
	}

	protected EntityDao<E, I> getDao()
	{
		return this.dao;
	};
}
