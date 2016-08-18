package net.dontdrinkandroot.persistence.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.persistence.dao.ExampleIdEntityDao;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database.xml" })
@Rollback
public class DaoEntityServiceTest
{

	@Autowired
	private ExampleIdEntityDao entityDao;

	private DaoEntityService<ExampleIdEntity, Long> entityService;


	@Before
	public void beforeMethod()
	{
		this.entityService = new DaoEntityService<>(this.entityDao);
	}

	private void populateDatabase()
	{
		for (long i = 0; i < 100; i++) {
			ExampleIdEntity entity = new ExampleIdEntity(i, Long.toString(i));
			this.entityDao.save(entity, false);
		}
		this.entityDao.flush();
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testFind()
	{
		this.populateDatabase();
		Assert.assertNull(this.entityService.find(1000L));
		ExampleIdEntity entity = this.entityService.find(10L);
		Assert.assertNotNull(entity);
		Assert.assertEquals(new Long(10), entity.getId());
		Assert.assertEquals(Integer.toString(10), entity.getText());
	}
}
