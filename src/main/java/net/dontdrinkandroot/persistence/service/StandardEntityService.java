package net.dontdrinkandroot.persistence.service;

import net.dontdrinkandroot.persistence.dao.TypedJpaDao;
import net.dontdrinkandroot.persistence.entity.Entity;


public class StandardEntityService<E extends Entity<I>, I> implements EntityService<E, I>
{

	private TypedJpaDao<E, I> dao;


	protected StandardEntityService()
	{
		/* Reflection Instantiation */
	}

	public StandardEntityService(TypedJpaDao<E, I> dao)
	{
		this.dao = dao;
	}
}
