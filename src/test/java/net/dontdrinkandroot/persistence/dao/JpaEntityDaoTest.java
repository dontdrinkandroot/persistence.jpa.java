/*
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

import net.dontdrinkandroot.persistence.entity.ExampleIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity_;
import net.dontdrinkandroot.persistence.predicatebuilder.NumericOperator;
import net.dontdrinkandroot.persistence.predicatebuilder.NumericPredicateBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:database.xml"})
@Rollback
public class JpaEntityDaoTest
{
    @PersistenceContext
    EntityManager entityManager;

    private JpaEntityDao<ExampleIdEntity, Long> dao;

    @Before
    public void beforeMethod()
    {
        this.dao = new JpaEntityDao<ExampleIdEntity, Long>(ExampleIdEntity.class);
        this.dao.setEntityManager(this.entityManager);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    public void findAllWithLimit()
    {
        this.populateDatabase();

        List<ExampleIdEntity> entities = this.dao.findAll(0, 10);
        Assert.assertEquals(10, entities.size());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    public void findAllWithSort()
    {
        this.populateDatabase();

        List<ExampleIdEntity> entities = this.dao.findAll(ExampleIdEntity_.text, true);
        Assert.assertEquals(101, entities.size());
        Assert.assertEquals("10", entities.get(2).getText());

        entities = this.dao.findAll(ExampleIdEntity_.text, false);

        Assert.assertEquals(101, entities.size());
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
                                10
                        )));
        Assert.assertEquals(89, entities.size());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    public void testFindSingle()
    {
        this.populateDatabase();

        ExampleIdEntity entity = this.dao.find(ExampleIdEntity_.text, "3");
        Assert.assertNotNull(entity);
        Assert.assertEquals(new Long(3), entity.getId());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    public void testFindSingleWithNoResult()
    {
        this.populateDatabase();

        ExampleIdEntity entity = this.dao.find(ExampleIdEntity_.text, "asdfjhasdfklhjasf");
        Assert.assertNull(entity);
    }

    @Test(expected = NonUniqueResultException.class)
    @Transactional(transactionManager = "transactionManager")
    public void testFindSingleWithDuplicate()
    {
        this.populateDatabase();

        ExampleIdEntity entity = this.dao.find(ExampleIdEntity_.text, "2");
    }

    private void populateDatabase()
    {
        for (long i = 0; i < 100; i++) {
            ExampleIdEntity entity = new ExampleIdEntity(i, Long.toString(i));
            entity.setNumber(i);
            this.dao.save(entity, false);
        }
        /* Duplicate */
        ExampleIdEntity entity = new ExampleIdEntity(100, "2");
        entity.setNumber(2L);
        this.dao.save(entity);
        this.dao.flush();
    }
}
