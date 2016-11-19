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

import net.dontdrinkandroot.persistence.ExampleEnum;
import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleGeneratedIdEntity_;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity;
import net.dontdrinkandroot.persistence.entity.ExampleIdEntity_;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;


public class ExampleGeneratedIdEntityDaoImpl extends JpaEntityDao<ExampleGeneratedIdEntity, Long>
        implements ExampleGeneratedIdEntityDao
{
    public ExampleGeneratedIdEntityDaoImpl()
    {
        super(ExampleGeneratedIdEntity.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExampleGeneratedIdEntity> findByOtherText(final String text)
    {
        final CriteriaBuilder builder = this.getCriteriaBuilder();
        final CriteriaQuery<ExampleGeneratedIdEntity> criteriaQuery = builder.createQuery(this.entityClass);
        criteriaQuery.distinct(true);
        final Root<ExampleGeneratedIdEntity> root = criteriaQuery.from(this.entityClass);

        final ListJoin<ExampleGeneratedIdEntity, ExampleIdEntity> join =
                root.join(ExampleGeneratedIdEntity_.otherEntities);

        criteriaQuery.where(builder.equal(join.get(ExampleIdEntity_.text), text));

        return this.find(criteriaQuery);
    }

    @Override
    @Transactional(readOnly = true)
    public ExampleEnum findMaxEnum()
    {
        final CriteriaBuilder builder = this.getCriteriaBuilder();
        final CriteriaQuery<ExampleEnum> criteriaQuery = builder.createQuery(ExampleEnum.class);
        final Root<ExampleGeneratedIdEntity> root = criteriaQuery.from(this.entityClass);

        criteriaQuery.select(root.get(ExampleGeneratedIdEntity_.exampleEnum));

        final List<ExampleEnum> enums = this.getEntityManager().createQuery(criteriaQuery).getResultList();
        ExampleEnum max = null;
        for (final ExampleEnum exampleEnum : enums) {
            if (max == null || exampleEnum.ordinal() > max.ordinal()) {
                max = exampleEnum;
            }
        }

        return max;
    }

    @Override
    @Transactional(readOnly = true)
    public ExampleGeneratedIdEntity findWithOthersFetchJoin(final Long id)
    {
        final CriteriaBuilder builder = this.getCriteriaBuilder();
        final CriteriaQuery<ExampleGeneratedIdEntity> criteriaQuery =
                builder.createQuery(ExampleGeneratedIdEntity.class);
        final Root<ExampleGeneratedIdEntity> root = criteriaQuery.from(this.entityClass);
        root.fetch(ExampleGeneratedIdEntity_.otherEntities);

        criteriaQuery.where(builder.equal(root.get(ExampleGeneratedIdEntity_.id), id));

        return this.findSingle(criteriaQuery);
    }
}
