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
package net.dontdrinkandroot.persistence.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import net.dontdrinkandroot.persistence.entity.NumericEntity;
import net.dontdrinkandroot.persistence.entity.NumericEntity_;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class NumericEntityDaoTest extends TypedJpaDao<NumericEntity, Long>
{

	public NumericEntityDaoTest()
	{
		super(NumericEntity.class);
	}


	@Test
	@Transactional
	public void someBla()
	{
		NumericEntity entity = new NumericEntity();
		entity = this.save(entity);

		Assert.assertEquals(entity, this.find(entity.getId()));
	}


	@Test
	@Transactional
	public void testQuot()
	{
		NumericEntity entity2 = new NumericEntity();
		entity2.setLongField(2L);
		entity2.setIntField(3);
		entity2 = this.save(entity2);

		final CriteriaBuilder builder = this.getCriteriaBuilder();
		final CriteriaQuery<Number> criteriaQuery = builder.createQuery(Number.class);
		final Root<NumericEntity> root = criteriaQuery.from(NumericEntity.class);
		final Expression<Number> quot =
				builder.quot(
						builder.prod(builder.literal(1f), root.get(NumericEntity_.intField)),
						root.get(NumericEntity_.longField));
		criteriaQuery.select(quot);

		final Number result = this.findSingle(criteriaQuery);
		Assert.assertEquals(Float.class, result.getClass());
		Assert.assertEquals(3 / 2f, result);
	}
}
