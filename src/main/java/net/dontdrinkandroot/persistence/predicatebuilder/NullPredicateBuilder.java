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
package net.dontdrinkandroot.persistence.predicatebuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;


/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class NullPredicateBuilder<T> implements PredicateBuilder<T>
{

	private final boolean isNull;

	private final SingularAttribute<? super T, ?> attribute;


	public NullPredicateBuilder(final SingularAttribute<? super T, ?> attribute, final boolean isNull)
	{
		this.attribute = attribute;
		this.isNull = isNull;
	}

	@Override
	public Predicate createPredicate(final CriteriaBuilder builder, final Path<? extends T> root)
	{
		if (this.isNull) {
			return builder.isNull(root.get(this.attribute));
		} else {
			return builder.isNotNull(root.get(this.attribute));
		}
	}

}
