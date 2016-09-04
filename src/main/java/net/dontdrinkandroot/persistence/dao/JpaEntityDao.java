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
package net.dontdrinkandroot.persistence.dao;

import net.dontdrinkandroot.persistence.entity.Entity;
import net.dontdrinkandroot.persistence.predicatebuilder.PredicateBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;


/**
 * Base implementation of a {@link EntityDao} that uses a JPA {@link EntityManager}.
 *
 * @param <E>
 *            Type of the {@link Entity}.
 * @param <I>
 *            Type of the {@link Entity}'s id.
 *
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class JpaEntityDao<E extends Entity<I>, I> extends JpaGenericDao implements EntityDao<E, I>
{

	protected Class<E> entityClass;


	public JpaEntityDao(final Class<E> entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(final E entity)
	{
		super.delete(entity, this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(final I id)
	{
		super.delete(id, this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public E find(final I id)
	{
		return super.find(id, this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public long getCount()
	{
		return super.getCount(this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public E load(final I id)
	{
		return super.load(id, this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll()
	{
		return super.findAll(this.entityClass);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(final SingularAttribute<? super E, ?> attribute, final boolean asc)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		if (asc) {
			criteriaQuery.orderBy(builder.asc(from.get(attribute)));
		} else {
			criteriaQuery.orderBy(builder.desc(from.get(attribute)));
		}

		return this.find(criteriaQuery);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(final int firstResult, final int maxResults)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		return this.find(criteriaQuery, firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(
			final SingularAttribute<? super E, ?> attribute,
			final boolean asc,
			final int firstResult,
			final int maxResults)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		if (attribute != null) {
			if (asc) {
				criteriaQuery.orderBy(builder.asc(from.get(attribute)));
			} else {
				criteriaQuery.orderBy(builder.desc(from.get(attribute)));
			}
		}

		return this.find(criteriaQuery, firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(final PredicateBuilder<E> filter)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		criteriaQuery.where(filter.createPredicate(builder, from));

		return this.find(criteriaQuery);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(final Collection<PredicateBuilder<E>> filters)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		final Predicate[] predicates = new Predicate[filters.size()];
		int count = 0;
		for (final PredicateBuilder<E> filter : filters) {
			predicates[count] = filter.createPredicate(builder, from);
			count++;
		}

		criteriaQuery.where(predicates);

		return this.find(criteriaQuery);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public List<E> findAll(final PredicateBuilder<E>... filters)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(this.entityClass);
		final Root<E> from = criteriaQuery.from(this.entityClass);

		final Predicate[] predicates = new Predicate[filters.length];
		int count = 0;
		for (final PredicateBuilder<E> filter : filters) {
			predicates[count] = filter.createPredicate(builder, from);
			count++;
		}

		criteriaQuery.where(predicates);

		return this.find(criteriaQuery);
	}
}
