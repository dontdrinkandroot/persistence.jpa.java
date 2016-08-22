package net.dontdrinkandroot.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.dontdrinkandroot.persistence.entity.ExampleIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity_;


/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database.xml" })
@Rollback
public class GenericJpaDaoTest
{

	@PersistenceContext
	EntityManager entityManager;

	private GenericJpaDao dao;


	@Before
	public void beforeMethod()
	{
		this.dao = new GenericJpaDao();
		this.dao.setEntityManager(this.entityManager);
	}

	@Test
	public void testLogger()
	{
		Logger logger = LoggerFactory.getLogger(GenericJpaDaoTest.class);
		this.dao.setLogger(logger);
		Assert.assertEquals(logger, this.dao.getLogger());
	}

	@Test(expected = NoResultException.class)
	@Transactional(transactionManager = "transactionManager")
	public void testLoadNonExisting()
	{
		this.dao.load(new Long(1L), ExampleIdEntity.class);
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testSaveFindDelete()
	{
		Assert.assertEquals(0, this.dao.getCount(ExampleIdEntity.class));
		ExampleIdEntity entity = new ExampleIdEntity(1L, "one");
		this.dao.save(entity);
		Assert.assertEquals(1, this.dao.getCount(ExampleIdEntity.class));
		Assert.assertEquals(entity, this.dao.find(1L, ExampleIdEntity.class));
		this.dao.delete(entity, ExampleIdEntity.class);
		Assert.assertEquals(0, this.dao.getCount(ExampleIdEntity.class));

		entity = new ExampleIdEntity(2L, "two");
		this.dao.persist(entity);
		Assert.assertEquals(1, this.dao.getCount(ExampleIdEntity.class));
		this.dao.delete((ExampleIdEntity) null, ExampleIdEntity.class);
		this.dao.delete((Long) null, ExampleIdEntity.class);
		Assert.assertEquals(1, this.dao.getCount(ExampleIdEntity.class));
		this.dao.delete(new Long(3L), ExampleIdEntity.class);
		Assert.assertEquals(1, this.dao.getCount(ExampleIdEntity.class));
		this.dao.delete(new Long(2L), ExampleIdEntity.class);
		Assert.assertEquals(0, this.dao.getCount(ExampleIdEntity.class));
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void testFindSingleOrNull()
	{
		this.populateDatabase();

		CriteriaBuilder criteriaBuilder = this.dao.getCriteriaBuilder();

		CriteriaQuery<ExampleIdEntity> query = criteriaBuilder.createQuery(ExampleIdEntity.class);
		Root<ExampleIdEntity> root = query.from(ExampleIdEntity.class);
		query.where(criteriaBuilder.equal(root.get(ExampleIdEntity_.id), 1L));
		Assert.assertEquals(new Long(1L), this.dao.findSingleOrNull(query).getId());

		query = criteriaBuilder.createQuery(ExampleIdEntity.class);
		root = query.from(ExampleIdEntity.class);
		query.where(criteriaBuilder.equal(root.get(ExampleIdEntity_.id), 120L));
		Assert.assertNull(this.dao.findSingleOrNull(query));
	}

	private void populateDatabase()
	{
		for (long i = 0; i < 100; i++) {
			ExampleIdEntity entity = new ExampleIdEntity(i, Long.toString(i));
			this.dao.save(entity, false);
		}
		this.dao.flush();
	}
}
