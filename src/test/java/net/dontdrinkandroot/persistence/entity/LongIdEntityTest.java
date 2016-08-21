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
