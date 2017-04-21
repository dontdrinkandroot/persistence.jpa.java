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
package net.dontdrinkandroot.persistence.service;

import net.dontdrinkandroot.persistence.entity.Entity;
import net.dontdrinkandroot.persistence.pagination.PaginatedResult;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface EntityService<E extends Entity<I>, I>
{
    E find(I id);

    E save(E entity);

    List<E> listAll();

    List<E> listAll(long first, long count);

    List<E> listAll(long first, long count, SingularAttribute<? super E, ?> sortAttribute, boolean asc);

    void delete(E entity);

    PaginatedResult<E> listPaginated(int page, int perPage);

    PaginatedResult<E> listPaginated(int page, int perPage, SingularAttribute<? super E, ?> sortAttribute, boolean asc);

    long findCount();
}
