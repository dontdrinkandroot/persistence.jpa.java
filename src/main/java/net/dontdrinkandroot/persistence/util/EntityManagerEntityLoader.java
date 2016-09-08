package net.dontdrinkandroot.persistence.util;

import net.dontdrinkandroot.persistence.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class EntityManagerEntityLoader implements EntityLoader
{
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public <T extends Entity<K>, K> T load(K id, Class<T> entityClass)
    {
        T entity = this.entityManager.find(entityClass, id);
        if (null == entity) {
            throw new NoResultException();
        }

        return entity;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}
