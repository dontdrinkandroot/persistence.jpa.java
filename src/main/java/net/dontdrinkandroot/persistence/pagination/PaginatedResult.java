/**
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
package net.dontdrinkandroot.persistence.pagination;

import java.io.Serializable;
import java.util.List;


/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PaginatedResult<T> implements Serializable
{

    private final Pagination pagination;

    private final List<T> entries;

    public PaginatedResult(Pagination pagination, List<T> entries)
    {
        this.pagination = pagination;
        this.entries = entries;
    }

    public Pagination getPagination()
    {
        return this.pagination;
    }

    public List<T> getEntries()
    {
        return this.entries;
    }
}
