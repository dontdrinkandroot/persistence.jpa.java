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
package net.dontdrinkandroot.persistence.dao;

import java.util.List;

import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity_;
import net.dontdrinkandroot.persistence.predicatebuilder.NullPredicateBuilder;
import net.dontdrinkandroot.persistence.predicatebuilder.NumericPredicateBuilder;
import net.dontdrinkandroot.persistence.predicatebuilder.NumericOperator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class PredicateBuilderTest {

	@Autowired
	private ExampleGeneratedIdEntityDao generatedIdEntityDao;


	@Test
	@Transactional
	public void testNumericFilters() {

		ExampleGeneratedIdEntity e1 = new ExampleGeneratedIdEntity().setNumericValue(1);
		e1 = this.generatedIdEntityDao.save(e1);
		ExampleGeneratedIdEntity e2 = new ExampleGeneratedIdEntity().setNumericValue(2);
		e2 = this.generatedIdEntityDao.save(e2);
		ExampleGeneratedIdEntity e3 = new ExampleGeneratedIdEntity().setNumericValue(3);
		e3 = this.generatedIdEntityDao.save(e3);
		ExampleGeneratedIdEntity e4 = new ExampleGeneratedIdEntity().setNumericValue(4);
		e4 = this.generatedIdEntityDao.save(e4);
		ExampleGeneratedIdEntity e5 = new ExampleGeneratedIdEntity().setNumericValue(5);
		e5 = this.generatedIdEntityDao.save(e5);

		final NumericPredicateBuilder<ExampleGeneratedIdEntity> lt2 =
				new NumericPredicateBuilder<ExampleGeneratedIdEntity>(
						ExampleGeneratedIdEntity_.numericValue,
						NumericOperator.LESS_THAN,
						2);
		final NumericPredicateBuilder<ExampleGeneratedIdEntity> le1 =
				new NumericPredicateBuilder<ExampleGeneratedIdEntity>(
						ExampleGeneratedIdEntity_.numericValue,
						NumericOperator.LESS_EQUALS,
						1);
		final NumericPredicateBuilder<ExampleGeneratedIdEntity> eq3 =
				new NumericPredicateBuilder<ExampleGeneratedIdEntity>(
						ExampleGeneratedIdEntity_.numericValue,
						NumericOperator.EQUALS,
						3);
		final NumericPredicateBuilder<ExampleGeneratedIdEntity> ge5 =
				new NumericPredicateBuilder<ExampleGeneratedIdEntity>(
						ExampleGeneratedIdEntity_.numericValue,
						NumericOperator.GREATER_EQUALS,
						5);
		final NumericPredicateBuilder<ExampleGeneratedIdEntity> gt4 =
				new NumericPredicateBuilder<ExampleGeneratedIdEntity>(
						ExampleGeneratedIdEntity_.numericValue,
						NumericOperator.GREATER_THAN,
						4);

		List<ExampleGeneratedIdEntity> result = this.generatedIdEntityDao.findAll(lt2);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e1, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(le1);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e1, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(eq3);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e3, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(ge5);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e5, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(gt4);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e5, result.iterator().next());
	}


	@Test
	@Transactional
	public void testNullFilters() {

		ExampleGeneratedIdEntity e1 = new ExampleGeneratedIdEntity();
		e1 = this.generatedIdEntityDao.save(e1);
		ExampleGeneratedIdEntity e2 = new ExampleGeneratedIdEntity().setNumericValue(1);
		e2 = this.generatedIdEntityDao.save(e2);

		final NullPredicateBuilder<ExampleGeneratedIdEntity> isNullFilter =
				new NullPredicateBuilder<ExampleGeneratedIdEntity>(ExampleGeneratedIdEntity_.numericValue, true);

		final NullPredicateBuilder<ExampleGeneratedIdEntity> notNullFilter =
				new NullPredicateBuilder<ExampleGeneratedIdEntity>(ExampleGeneratedIdEntity_.numericValue, false);

		List<ExampleGeneratedIdEntity> result = this.generatedIdEntityDao.findAll(isNullFilter);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e1, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(notNullFilter);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(e2, result.iterator().next());

		result = this.generatedIdEntityDao.findAll(notNullFilter, isNullFilter);
		Assert.assertEquals(0, result.size());
	}
}
