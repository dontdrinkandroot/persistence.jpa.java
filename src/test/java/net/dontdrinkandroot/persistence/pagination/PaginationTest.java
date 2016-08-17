package net.dontdrinkandroot.persistence.pagination;

import org.junit.Assert;
import org.junit.Test;


public class PaginationTest
{

	@Test
	public void testInvalid()
	{
		try {
			new Pagination(-1, 0, 0);
			Assert.fail("Exception expected");
		} catch (IllegalArgumentException e) {
			/* Expected */
		}

		try {
			new Pagination(1, 0, 0);
			Assert.fail("Exception expected");
		} catch (IllegalArgumentException e) {
			/* Expected */
		}

		try {
			new Pagination(1, 1, -1);
			Assert.fail("Exception expected");
		} catch (IllegalArgumentException e) {
			/* Expected */
		}
	}

	@Test
	public void testGetTotalPages()
	{
		Pagination pagination = new Pagination(1, 1, 0);
		Assert.assertEquals(0, pagination.getTotalPages());

		pagination = new Pagination(1, 1, 1);
		Assert.assertEquals(1, pagination.getTotalPages());

		pagination = new Pagination(1, 1, 2);
		Assert.assertEquals(2, pagination.getTotalPages());

		pagination = new Pagination(1, 2, 2);
		Assert.assertEquals(1, pagination.getTotalPages());
	}
}
