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
package net.dontdrinkandroot.persistence.predicatebuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import net.dontdrinkandroot.persistence.entity.Entity;


/**
 * A PredicateBuillder is an abstraction to generate a {@link Predicate} for an {@link Entity}.
 * 
 * @param <E>
 *            Type of the {@link Entity} to build the {@link Predicate} for.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface PredicateBuilder<E> {

	/**
	 * Builds a predicate with the given builder that is derived from the given path.
	 */
	Predicate createPredicate(CriteriaBuilder builder, Path<? extends E> path);

}
