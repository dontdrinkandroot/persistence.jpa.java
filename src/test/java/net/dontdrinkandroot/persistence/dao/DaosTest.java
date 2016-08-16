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
package net.dontdrinkandroot.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.persistence.ExampleEnum;
import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity_;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DaosTest
{

	@Autowired
	private ExampleIdEntityDao entityDao;

	@Autowired
	private ExampleGeneratedIdEntityDao generatedIdEntityDao;


	@Test
	@Transactional
	public void testExampleEntityDao()
	{
		Assert.assertEquals(0, this.entityDao.findAll().size());

		final ExampleIdEntity ex1 = new ExampleIdEntity(1L);
		this.entityDao.save(ex1);
		Assert.assertEquals(ex1, this.entityDao.load(1L));

		final ExampleIdEntity ex2 = new ExampleIdEntity(2L);
		this.entityDao.save(ex2);
		Assert.assertEquals(ex2, this.entityDao.find(2L));

		Assert.assertEquals(2, this.entityDao.findAll().size());

		this.entityDao.delete((ExampleIdEntity) null);
		Assert.assertEquals(2, this.entityDao.findAll().size());

		this.entityDao.delete(ex2);
		Assert.assertNull(this.entityDao.find(2L));
		Assert.assertEquals(ex1, this.entityDao.find(1L));
		Assert.assertEquals(1, this.entityDao.findAll().size());

		this.entityDao.delete(ex1);
		Assert.assertNull(this.entityDao.find(1L));
		Assert.assertNull(this.entityDao.find(2L));
		Assert.assertEquals(0, this.entityDao.findAll().size());
	}

	@Test
	@Transactional
	public void testDeleteById()
	{
		Assert.assertEquals(0, this.entityDao.findAll().size());

		final ExampleIdEntity ex1 = new ExampleIdEntity(1L);
		this.entityDao.save(ex1);
		Assert.assertEquals(ex1, this.entityDao.find(1L));

		final ExampleIdEntity ex2 = new ExampleIdEntity(2L);
		this.entityDao.save(ex2);
		Assert.assertEquals(ex2, this.entityDao.find(2L));

		Assert.assertEquals(2, this.entityDao.findAll().size());

		this.entityDao.delete((Long) null);
		Assert.assertEquals(2, this.entityDao.findAll().size());

		this.entityDao.delete(3L);
		Assert.assertEquals(2, this.entityDao.findAll().size());

		this.entityDao.delete(2L);
		Assert.assertNull(this.entityDao.find(2L));
		Assert.assertEquals(ex1, this.entityDao.find(1L));
		Assert.assertEquals(1, this.entityDao.findAll().size());

		this.entityDao.delete(1L);
		Assert.assertNull(this.entityDao.find(1L));
		Assert.assertNull(this.entityDao.find(2L));
		Assert.assertEquals(0, this.entityDao.findAll().size());
	}

	@Test
	@Transactional
	public void testExampleGeneratedIdEntityDao()
	{
		Assert.assertEquals(0, this.generatedIdEntityDao.findAll().size());

		ExampleGeneratedIdEntity ex1 = new ExampleGeneratedIdEntity();
		ex1 = this.generatedIdEntityDao.save(ex1);
		Assert.assertEquals(ex1, this.generatedIdEntityDao.find(ex1.getId()));

		ExampleGeneratedIdEntity ex2 = new ExampleGeneratedIdEntity();
		ex2 = this.generatedIdEntityDao.save(ex2);
		Assert.assertEquals(ex2, this.generatedIdEntityDao.find(ex2.getId()));

		Assert.assertEquals(2, this.generatedIdEntityDao.findAll().size());

		this.generatedIdEntityDao.delete(ex2);
		Assert.assertNull(this.generatedIdEntityDao.find(ex2.getId()));
		Assert.assertEquals(ex1, this.generatedIdEntityDao.find(ex1.getId()));
		Assert.assertEquals(1, this.generatedIdEntityDao.findAll().size());

		this.generatedIdEntityDao.delete(ex1);
		Assert.assertNull(this.generatedIdEntityDao.find(ex1.getId()));
		Assert.assertNull(this.generatedIdEntityDao.find(ex2.getId()));
		Assert.assertEquals(0, this.generatedIdEntityDao.findAll().size());
	}

	@Test
	@Transactional
	public void testGetCount()
	{
		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity());
		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity());

		Assert.assertEquals(2, this.generatedIdEntityDao.getCount());

		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity());
		Assert.assertEquals(3, this.generatedIdEntityDao.getCount());
	}

	@Test
	@Transactional
	public void testFindMaxEnum()
	{

		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity().setExampleEnum(ExampleEnum.FIRST));
		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity().setExampleEnum(ExampleEnum.SECOND));
		this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity().setExampleEnum(ExampleEnum.FIRST));

		Assert.assertEquals(ExampleEnum.SECOND, this.generatedIdEntityDao.findMaxEnum());
	}

	@Test
	@Transactional
	public void testFindPaginated()
	{
		final ExampleGeneratedIdEntity ex1 =
				this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity()).setNumericValue(4);
		final ExampleGeneratedIdEntity ex2 =
				this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity()).setNumericValue(2);
		final ExampleGeneratedIdEntity ex3 =
				this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity()).setNumericValue(3);
		final ExampleGeneratedIdEntity ex4 =
				this.generatedIdEntityDao.save(new ExampleGeneratedIdEntity()).setNumericValue(1);

		List<ExampleGeneratedIdEntity> result =
				this.generatedIdEntityDao.findAll(ExampleGeneratedIdEntity_.numericValue, true, 0, 2);
		Assert.assertEquals(2, result.size());

		Assert.assertEquals(ex4, result.get(0));
		Assert.assertEquals(ex2, result.get(1));

		result = this.generatedIdEntityDao.findAll(ExampleGeneratedIdEntity_.numericValue, false, 2, 2);
		Assert.assertEquals(2, result.size());

		Assert.assertEquals(ex2, result.get(0));
		Assert.assertEquals(ex4, result.get(1));
	}

	@Test
	@Transactional
	public void testFindByJoin()
	{
		ExampleIdEntity otherOne = new ExampleIdEntity(1, "one");
		ExampleIdEntity otherTwo = new ExampleIdEntity(2, "two");
		ExampleIdEntity otherThree = new ExampleIdEntity(3, "three");

		otherOne = this.entityDao.save(otherOne);
		otherTwo = this.entityDao.save(otherTwo);
		otherThree = this.entityDao.save(otherThree);

		Assert.assertEquals(0, this.generatedIdEntityDao.findAll().size());

		ExampleGeneratedIdEntity ex1 = new ExampleGeneratedIdEntity();

		final List<ExampleIdEntity> ex1Others = new ArrayList<ExampleIdEntity>();
		ex1Others.add(otherOne);
		ex1Others.add(otherTwo);
		ex1.setOtherEntities(ex1Others);

		ex1 = this.generatedIdEntityDao.save(ex1);
		Assert.assertEquals(ex1, this.generatedIdEntityDao.find(ex1.getId()));

		ExampleGeneratedIdEntity ex2 = new ExampleGeneratedIdEntity();

		final List<ExampleIdEntity> ex2Others = new ArrayList<ExampleIdEntity>();
		ex2Others.add(otherOne);
		ex2Others.add(otherThree);
		ex2.setOtherEntities(ex2Others);

		ex2 = this.generatedIdEntityDao.save(ex2);
		Assert.assertEquals(ex2, this.generatedIdEntityDao.find(ex2.getId()));

		final List<ExampleGeneratedIdEntity> oneResults = this.generatedIdEntityDao.findByOtherText("one");
		Assert.assertEquals(2, oneResults.size());
		Assert.assertTrue(oneResults.contains(ex1));
		Assert.assertTrue(oneResults.contains(ex2));

		final List<ExampleGeneratedIdEntity> threeResults = this.generatedIdEntityDao.findByOtherText("three");
		Assert.assertEquals(1, threeResults.size());
		Assert.assertEquals(ex2, threeResults.iterator().next());
	}

}
