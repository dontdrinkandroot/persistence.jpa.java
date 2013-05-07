/**
 * Copyright (C) 2012, 2013 Philip W. Sorst <philip@sorst.net>
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
import javax.persistence.metamodel.SingularAttribute;


public class NumericPredicateBuilder<T> implements PredicateBuilder<T> {

	private final NumericOperator operator;

	private final Number literal;

	private final SingularAttribute<? super T, ? extends Number> attribute;


	public NumericPredicateBuilder(
			final SingularAttribute<? super T, ? extends Number> attribute,
			final NumericOperator operator,
			final Number literal) {

		this.attribute = attribute;
		this.operator = operator;
		this.literal = literal;
	}


	@Override
	public Predicate createPredicate(final CriteriaBuilder builder, final Path<? extends T> root) {

		switch (this.operator) {

			case EQUALS:
				return builder.equal(root.get(this.attribute), this.literal);

			case GREATER_EQUALS:
				return builder.ge(root.get(this.attribute), this.literal);

			case GREATER_THAN:
				return builder.gt(root.get(this.attribute), this.literal);

			case LESS_EQUALS:
				return builder.le(root.get(this.attribute), this.literal);

			case LESS_THAN:
				return builder.lt(root.get(this.attribute), this.literal);

			default:
				throw new RuntimeException("Unknown operator " + this.operator);
		}
	}

}
