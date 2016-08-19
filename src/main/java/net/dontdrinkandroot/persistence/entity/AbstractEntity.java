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
package net.dontdrinkandroot.persistence.entity;

import javax.persistence.MappedSuperclass;


/**
 * Represents an entity with a primary key. The method getId() must be implemented, even if the primary key has a
 * different field name, also make sure that the primary key implements equals/hashcode correctly. hashCode() and
 * equals() are based on the id, so make sure not to use id in any context where these methods are important while no id
 * is set (e.g. in HashMaps) or you must override them.
 *
 * @param <K>
 *            Type of the primary key.
 *
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@MappedSuperclass
public abstract class AbstractEntity<K> implements Entity<K>
{

	/**
	 * Constructs an entity without the primary key being set.
	 */
	public AbstractEntity()
	{
		/* Noop */
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + "[id=" + this.getId() + "]";
	}

	@Override
	public int hashCode()
	{
		/* Only considers the id if set */
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.getId() == null ? 0 : this.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj) {
			/* Identity */
			return true;
		}

		if (obj == null) {
			/* Other is null */
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			/* Classes don't match */
			return false;
		}

		final AbstractEntity<?> other = (AbstractEntity<?>) obj;

		if (this.getId() == null) {
			if (other.getId() != null) {
				/* Both ids are null */
				return false;
			}
		} else if (!this.getId().equals(other.getId())) {
			/* Ids are not equal */
			return false;
		}

		return true;
	}

}
