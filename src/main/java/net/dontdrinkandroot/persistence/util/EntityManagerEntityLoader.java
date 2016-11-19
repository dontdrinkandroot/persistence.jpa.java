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
package net.dontdrinkandroot.persistence.util;

import net.dontdrinkandroot.persistence.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class EntityManagerEntityLoader implements EntityLoader
{
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public <T extends Entity<K>, K> T load(K id, Class<T> entityClass)
    {
        T entity = this.entityManager.find(entityClass, id);
        if (null == entity) {
            throw new NoResultException();
        }

        return entity;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}
