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
package net.dontdrinkandroot.persistence.entity;

import org.junit.Assert;
import org.junit.Test;


public class LongIdEntityTest
{

    @Test
    public void testHashCodeAndEqualsWithId()
    {
        LongIdEntity entity1 = new LongIdEntity(1L);
        LongIdEntity entity2 = new LongIdEntity(2L);
        LongIdEntity entity3 = new LongIdEntity(1L);
        LongIdEntity entity4 = new LongIdEntity(4l);
        Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());

        Assert.assertTrue(entity1.equals(entity1));
        Assert.assertTrue(entity1.equals(entity3));
        Assert.assertFalse(entity1.equals(entity2));
        Assert.assertFalse(entity1.equals(entity4));
        Assert.assertFalse(entity4.equals(entity1));
        Assert.assertFalse(entity1.equals(null));
        Assert.assertFalse(entity1.equals("test"));
    }

    @Test
    public void testHashCodeAndEqualsWithoutId()
    {
        LongIdEntity entity1 = new LongIdEntity();
        LongIdEntity entity2 = new LongIdEntity();
        LongIdEntity entity3 = new LongIdEntity(1L);
        Assert.assertEquals(entity1.hashCode(), entity2.hashCode());

        Assert.assertTrue(entity1.equals(entity1));
        Assert.assertTrue(entity1.equals(entity2));
        Assert.assertFalse(entity1.equals(null));
        Assert.assertFalse(entity1.equals(entity3));
        Assert.assertFalse(entity1.equals("test"));
    }

    @Test
    public void testToString()
    {
        LongIdEntity nullId = new LongIdEntity();
        LongIdEntity notNullId = new LongIdEntity(1L);

        Assert.assertEquals("LongIdEntity[id=null]", nullId.toString());
        Assert.assertEquals("LongIdEntity[id=1]", notNullId.toString());
    }

}
