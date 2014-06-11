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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import net.dontdrinkandroot.persistence.ExampleEnum;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@javax.persistence.Entity
public class ExampleGeneratedIdEntity extends GeneratedLongIdEntity
{

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<ExampleIdEntity> otherEntities;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private ExampleEnum exampleEnum;

	@Column
	private Integer numericValue;


	public ExampleGeneratedIdEntity()
	{
	}


	public void setOtherEntities(final List<ExampleIdEntity> otherEntities)
	{
		this.otherEntities = otherEntities;
	}


	public List<ExampleIdEntity> getOtherEntities()
	{
		return this.otherEntities;
	}


	public ExampleEnum getExampleEnum()
	{
		return this.exampleEnum;
	}


	public ExampleGeneratedIdEntity setExampleEnum(final ExampleEnum exampleEnum)
	{
		this.exampleEnum = exampleEnum;
		return this;
	}


	public Integer getNumericValue()
	{
		return this.numericValue;
	}


	public ExampleGeneratedIdEntity setNumericValue(final Integer numericValue)
	{
		this.numericValue = numericValue;
		return this;
	}

}
