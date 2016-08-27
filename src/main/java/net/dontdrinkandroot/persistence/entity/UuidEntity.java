package net.dontdrinkandroot.persistence.entity;

/**
 * @param <K>
 *            Type of the primary Key.
 * 
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface UuidEntity<K> extends Entity<K>
{

	String getUuid();
}
