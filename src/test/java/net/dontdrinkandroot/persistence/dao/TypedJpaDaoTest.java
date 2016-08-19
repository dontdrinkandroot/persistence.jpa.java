package net.dontdrinkandroot.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TypedJpaDaoTest
{

	@PersistenceContext
	EntityManager entityManager;

	private TypedJpaDao<ExampleIdEntity, Long> dao;


	@Before
	public void beforeMethod()
	{
		this.dao = new TypedJpaDao<ExampleIdEntity, Long>(ExampleIdEntity.class);
		this.dao.setEntityManager(this.entityManager);
	}

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void findAllWithSort()
	{
		this.populateDatabase();

		List<ExampleIdEntity> entities = this.dao.findAll(ExampleIdEntity_.text, true);
		Assert.assertEquals(100, entities.size());
		Assert.assertEquals("10", entities.get(2).getText());

		entities = this.dao.findAll(ExampleIdEntity_.text, false);

		Assert.assertEquals(100, entities.size());
		Assert.assertEquals("9", entities.get(10).getText());
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
