package net.dontdrinkandroot.persistence.entity;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 *
 * @param <K>
 *            Type of the primary Key.
 */
public interface UuidEntity<K> extends Entity<K>
{

	String getUuid();
}
