/*
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

import javax.persistence.NonUniqueResultException;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;

/**
 * Contract of a Data Access Object that manages a specific {@link Entity} class.
 *
 * @param <E> Type of the {@link Entity}.
 * @param <K> Type of the {@link Entity}'s primary id.
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface EntityDao<E extends Entity<K>, K> extends GenericDao
{
    /**
     * Deletes the given Entity.
     */
    void delete(E entity);

    /**
     * Deletes the entity with the given id.
     */
    void delete(K id);

    /**
     * Finds the entity with the given id.
     */
    E find(K id);

    /**
     * Finds all entities.
     */
    List<E> findAll();

    /**
     * Loads the entity with the given id or throws an Exception if it was not found.
     */
    E load(K id);

    /**
     * Finds a single entity by an attribute value.
     *
     * @param attribute The attribute to check.
     * @param value     The desired value of the attribute.
     * @param <T>       The type of the attribue.
     * @return The entity matching the attribute or null if non was found.
     * @throws NonUniqueResultException Thrown if more than one result was found.
     */
    <T> E find(SingularAttribute<? super E, T> attribute, T value);

    /**
     * Get the total number of entities in this Dao.
     */
    long getCount();

    /**
     * Finds all entities ordered by the given attribute and the given sort order.
     *
     * @param asc True to sort in ascending order, false to sort in descending order.
     */
    List<E> findAll(SingularAttribute<? super E, ?> attribute, boolean asc);

    /**
     * Finds all entities with the given limitations.
     *
     * @param firstResult Position of the first result to retrieve.
     * @param numResults  The maximum number of results to retrieve.
     */
    List<E> findAll(int firstResult, int numResults);

    /**
     * Finds all entities ordered by the given attribute and the given sort order with the result set limit to the given
     * size.
     *
     * @param attribute   The attribute to sort by.
     * @param asc         True to sort in ascending order, false to sort in descending order.
     * @param firstResult Position of the first result to retrieve.
     * @param numResults  The maximum number of results to retrieve.
     */
    List<E> findAll(SingularAttribute<? super E, ?> attribute, boolean asc, int firstResult, int numResults);

    /**
     * Finds all entities that match all predicates generated by the given {@link PredicateBuilder} s.
     */
    List<E> findAll(Collection<PredicateBuilder<E>> predicateBuilders);

    /**
     * Finds all entities that match all predicates generated by the given {@link PredicateBuilder} s.
     */
    List<E> findAll(PredicateBuilder<E> predicateBuilder);

    /**
     * Finds all entities that match all predicates generated by the given {@link PredicateBuilder} s.
     */
    List<E> findAll(PredicateBuilder<E>... predicateBuilders);
}
