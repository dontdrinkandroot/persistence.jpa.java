package net.dontdrinkandroot.persistence.dao;

import java.util.Collections;
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
import net.dontdrinkandroot.persistence.predicatebuilder.NumericOperator;
import net.dontdrinkandroot.persistence.predicatebuilder.NumericPredicateBuilder;


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

	@Test
	@Transactional(transactionManager = "transactionManager")
	public void findAllWithPredicateCollection()
	{
		this.populateDatabase();

		List<ExampleIdEntity> entities = this.dao.findAll(
				Collections.singletonList(
						new NumericPredicateBuilder<ExampleIdEntity>(
								ExampleIdEntity_.number,
								NumericOperator.GREATER_THAN,
								10)));
		Assert.assertEquals(89, entities.size());
	}

	private void populateDatabase()
	{
		for (long i = 0; i < 100; i++) {
			ExampleIdEntity entity = new ExampleIdEntity(i, Long.toString(i));
			entity.setNumber(i);
			this.dao.save(entity, false);
		}
		this.dao.flush();
	}
}
