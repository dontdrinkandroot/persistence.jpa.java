package net.dontdrinkandroot.persistence.service;

import java.util.List;

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
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity_;
import net.dontdrinkandroot.persistence.pagination.PaginatedResult;
import net.dontdrinkandroot.persistence.pagination.Pagination;


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

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testSave()
	{
		ExampleIdEntity entity = new ExampleIdEntity(33L);
		this.entityService.save(entity);
		entity = this.entityService.find(33L);
		Assert.assertEquals(new Long(33), entity.getId());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testCount()
	{
		Assert.assertEquals(0, this.entityService.findCount());
		this.populateDatabase();
		Assert.assertEquals(100, this.entityService.findCount());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testDelete()
	{
		this.populateDatabase();
		ExampleIdEntity entity = this.entityService.find(33L);
		Assert.assertNotNull(entity);
		this.entityService.delete(entity);
		Assert.assertNull(this.entityService.find(33L));
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testListAll()
	{
		Assert.assertEquals(0, this.entityService.listAll().size());
		this.populateDatabase();
		Assert.assertEquals(100, this.entityService.listAll().size());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testListAllWithLimit()
	{
		Assert.assertEquals(0, this.entityService.listAll().size());
		this.populateDatabase();
		Assert.assertEquals(33, this.entityService.listAll(1, 33).size());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testListAllWithSort()
	{
		Assert.assertEquals(0, this.entityService.listAll().size());
		this.populateDatabase();
		List<ExampleIdEntity> entities = this.entityService.listAll(1, 33, ExampleIdEntity_.text, true);
		Assert.assertEquals(33, entities.size());
		Assert.assertEquals("10", entities.get(1).getText());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testListPaginated()
	{
		Assert.assertEquals(0, this.entityService.listAll().size());
		this.populateDatabase();
		PaginatedResult<ExampleIdEntity> paginatedResult = this.entityService.listPaginated(1, 33);
		Assert.assertEquals(33, paginatedResult.getEntries().size());
		Pagination pagination = paginatedResult.getPagination();
		Assert.assertEquals(1, pagination.getCurrentPage());
		Assert.assertEquals(33, pagination.getPerPage());
		Assert.assertEquals(100, pagination.getTotal());
		Assert.assertEquals(4, pagination.getTotalPages());
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testListPaginatedWithSort()
	{
		Assert.assertEquals(0, this.entityService.listAll().size());
		this.populateDatabase();
		PaginatedResult<ExampleIdEntity> paginatedResult =
				this.entityService.listPaginated(1, 33, ExampleIdEntity_.text, true);
		Assert.assertEquals(33, paginatedResult.getEntries().size());
		Pagination pagination = paginatedResult.getPagination();
		Assert.assertEquals(1, pagination.getCurrentPage());
		Assert.assertEquals(33, pagination.getPerPage());
		Assert.assertEquals(100, pagination.getTotal());
		Assert.assertEquals(4, pagination.getTotalPages());
		Assert.assertEquals("10", paginatedResult.getEntries().get(2).getText());
	}
}
