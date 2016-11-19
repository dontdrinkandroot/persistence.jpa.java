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
package net.dontdrinkandroot.persistence.pagination;

import java.io.Serializable;


/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class Pagination implements Serializable
{
    private final int perPage;

    private final int currentPage;

    private final int total;

    public Pagination(int currentPage, int perPage, int total)
    {
        if (currentPage < 1) {
            throw new IllegalArgumentException("CurrentPage must be greater than 0");
        }

        if (perPage < 1) {
            throw new IllegalArgumentException("PerPage mustbe greater than 0");
        }

        if (total < 0) {
            throw new IllegalArgumentException("Total must be greater equals 0");
        }

        this.perPage = perPage;
        this.currentPage = currentPage;
        this.total = total;
    }

    public int getCurrentPage()
    {
        return this.currentPage;
    }

    public int getTotal()
    {
        return this.total;
    }

    public int getTotalPages()
    {
        if (this.total == 0) {
            return 0;
        }

        return (this.total - 1) / this.perPage + 1;
    }

    public int getPerPage()
    {
        return this.perPage;
    }
}
