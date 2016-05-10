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

import net.dontdrinkandroot.persistence.entity.Entity;


/**
 * General contract of a Data Access Object that manages {@link Entity} instances.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface GenericDao
{

	/**
	 * Adds the given entity to the persistence context and performs a flush.
	 *
	 * @return The saved instance of the entity.
	 */
	<E extends Entity<K>, K> E persist(E entity);

	/**
	 * Adds the given entity to the persistence context.
	 *
	 * @return The saved instance of the entity.
	 */
	<E extends Entity<K>, K> E persist(E entity, boolean flush);

	/**
	 * Saves the given entity.
	 *
	 * @return The saved instance of the entity.
	 */
	<E extends Entity<K>, K> E save(E entity);

	/**
	 * Saves the given entity.
	 *
	 * @param flush
	 *            Whether to flush after saving.
	 * @return The saved instance of the entity.
	 */
	<E extends Entity<K>, K> E save(E entity, boolean flush);

	/**
	 * Deletes the given entity of the given class.
	 */
	<E extends Entity<K>, K> void delete(final E entity, final Class<E> clazz);

	/**
	 * Deletes the entity with the given id of the given class.
	 */
	<E extends Entity<K>, K> void delete(final K id, final Class<E> clazz);

	/**
	 * Finds the entity with the given id of the given class.
	 */
	<E extends Entity<K>, K> E find(final K id, final Class<E> clazz);

	/**
	 * Finds all entities of the given class.
	 */
	<E extends Entity<K>, K> List<E> findAll(final Class<E> clazz);

	/**
	 * Loads the entity with the given id of the given class or throws an Exception if it was not found.
	 */
	<E extends Entity<K>, K> E load(final K id, final Class<E> clazz);

	/**
	 * Counts the entities of the given class.
	 */
	<E extends Entity<K>, K> long getCount(final Class<E> clazz);

}
