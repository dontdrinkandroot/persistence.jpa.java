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

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.dontdrinkandroot.persistence.ExampleEnum;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExampleGeneratedIdEntity.class)
public abstract class ExampleGeneratedIdEntity_ extends net.dontdrinkandroot.persistence.entity.GeneratedLongIdEntity_ {

	public static volatile SingularAttribute<ExampleGeneratedIdEntity, Integer> numericValue;
	public static volatile SingularAttribute<ExampleGeneratedIdEntity, ExampleEnum> exampleEnum;
	public static volatile ListAttribute<ExampleGeneratedIdEntity, ExampleIdEntity> otherEntities;

}

