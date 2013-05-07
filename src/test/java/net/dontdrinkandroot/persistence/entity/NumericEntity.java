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
package net.dontdrinkandroot.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class NumericEntity extends AbstractGeneratedIdEntity<Long> {

	@Column
	private Float floatField;

	@Column
	private Double doubleField;

	@Column
	private Integer intField;

	@Column
	private Long longField;


	public Float getFloatField() {

		return this.floatField;
	}


	public void setFloatField(final Float floatField) {

		this.floatField = floatField;
	}


	public Double getDoubleField() {

		return this.doubleField;
	}


	public void setDoubleField(final Double doubleField) {

		this.doubleField = doubleField;
	}


	public Long getLongField() {

		return this.longField;
	}


	public void setLongField(final Long longField) {

		this.longField = longField;
	}


	public Integer getIntField() {

		return this.intField;
	}


	public void setIntField(final Integer intField) {

		this.intField = intField;
	}

}
