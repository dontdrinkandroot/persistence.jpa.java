package net.dontdrinkandroot.persistence.util;

import net.dontdrinkandroot.persistence.entity.Entity;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface EntityLoader
{
	<T extends Entity<K>, K> T load(K id, Class<T> entityClass);
}
