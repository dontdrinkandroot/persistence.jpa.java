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

import javax.persistence.NoResultException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface EntityLoader
{
    /**
     * Loads an entity by its it.
     *
     * @param id          The id to look for.
     * @param entityClass The class of the entity.
     * @param <T>         The type of the entity.
     * @param <K>         The type of the primary key of the entity.
     * @return The entity.
     * @throws NoResultException Thrown if the entity was not found.
     */
    <T extends Entity<K>, K> T load(K id, Class<T> entityClass);
}
