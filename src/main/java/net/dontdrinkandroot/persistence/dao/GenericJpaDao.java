/**
 * Copyright (C) 2012-2014 Philip W. Sorst <philip@sorst.net>
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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import net.dontdrinkandroot.persistence.entity.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Base implementation of a {@link GenericDao} that uses a JPA {@link EntityManager}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class GenericJpaDao implements GenericDao
{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private EntityManager entityManager;


	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}


	public EntityManager getEntityManager()
	{
		return this.entityManager;
	}


	public Logger getLogger()
	{
		return this.logger;
	}


	public void setLogger(final Logger logger)
	{
		this.logger = logger;
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public <E extends Entity<K>, K> void delete(final E entity, final Class<E> clazz)
	{
		if (entity == null) {
			return;
		}

		/* Make sure entity is managed before deleting */
		E managedEntity = entity;
		if (!this.getEntityManager().contains(entity)) {
			managedEntity = this.getEntityManager().find(clazz, entity.getId());
		}

		this.getEntityManager().remove(managedEntity);
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public <E extends Entity<K>, K> void delete(final K id, final Class<E> clazz)
	{
		if (id == null) {
			return;
		}

		final E entity = this.getEntityManager().find(clazz, id);

		if (entity != null) {
			this.getEntityManager().remove(entity);
		}
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public <E extends Entity<K>, K> E find(final K id, final Class<E> clazz)
	{
		return this.getEntityManager().find(clazz, id);
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public <E extends Entity<K>, K> List<E> findAll(final Class<E> clazz)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<E> criteriaQuery = builder.createQuery(clazz);
		criteriaQuery.from(clazz);

		return this.find(criteriaQuery);
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public <E extends Entity<K>, K> E load(final K id, final Class<E> clazz)
	{
		final E entity = this.getEntityManager().find(clazz, id);
		if (entity == null) {
			throw new NoResultException();
		}

		return entity;
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public <E extends Entity<K>, K> E save(final E entity)
	{
		final E mergedEntity = this.getEntityManager().merge(entity);

		return mergedEntity;
	}


	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public <E extends Entity<K>, K> long getCount(final Class<E> clazz)
	{
		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<E> from = criteriaQuery.from(clazz);

		final Expression<Long> count = builder.count(from);

		criteriaQuery.select(count);

		return this.findSingle(criteriaQuery).longValue();
	}


	/**
	 * Gets the criteria builder of the entity manager.
	 */
	protected CriteriaBuilder getCriteriaBuilder()
	{
		return this.getEntityManager().getCriteriaBuilder();
	}


	/**
	 * Find all entities by the given {@link CriteriaQuery}.
	 */
	protected <V> List<V> find(final CriteriaQuery<V> criteriaQuery)
	{
		return this.getEntityManager().createQuery(criteriaQuery).getResultList();
	}


	/**
	 * Finds all entities by the given {@link CriteriaQuery} and limit the result set.
	 * 
	 * @param maxResults
	 *            The maximum number of results to retrieve.
	 */
	protected <V> List<V> find(final CriteriaQuery<V> criteriaQuery, final int maxResults)
	{
		final TypedQuery<V> query = this.getEntityManager().createQuery(criteriaQuery);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}


	/**
	 * Finds all entities by the given {@link CriteriaQuery} and limit the result set.
	 * 
	 * @param firstResult
	 *            Position of the first result to retrive.
	 * @param maxResults
	 *            The maximum number of results to retrieve.
	 */
	protected <V> List<V> find(final CriteriaQuery<V> criteriaQuery, final int firstResult, final int maxResults)
	{
		final TypedQuery<V> query = this.getEntityManager().createQuery(criteriaQuery);
		query.setMaxResults(maxResults);
		query.setFirstResult(firstResult);

		return query.getResultList();
	}


	/**
	 * Find a single entity by the given {@link CriteriaQuery}, throws an Exception if there is no or more than one
	 * result.
	 */
	protected <V> V findSingle(final CriteriaQuery<V> criteriaQuery)
	{
		return this.getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}


	/**
	 * Finds a single entity by the given {@link CriteriaQuery}, throws an Exception if there is more than one result.
	 * 
	 * @return The found entity or null if there was none found.
	 */
	protected <V> V findSingleOrNull(final CriteriaQuery<V> criteriaQuery)
	{
		try {
			return this.getEntityManager().createQuery(criteriaQuery).getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}


	/**
	 * Finds the first entity by the given {@link CriteriaQuery} or null if no result was found.
	 * 
	 * @param criteriaQuery
	 *            Query to execute.
	 * @return The found entity or null if there was none found.
	 */
	protected <V> V findFirstOrNull(CriteriaQuery<V> criteriaQuery)
	{
		List<V> results = this.find(criteriaQuery, 1);
		if (results.isEmpty()) {
			return null;
		}

		return results.iterator().next();
	}
}
