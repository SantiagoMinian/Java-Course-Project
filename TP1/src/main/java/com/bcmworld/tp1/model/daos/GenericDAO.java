package com.bcmworld.tp1.model.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GenericDAO<T, I> {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("HibernatePersistence");
    private EntityManager manager;
    private Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T object) {

        createEntityManager();

        manager.persist(object);

        closeEntityManager();
    }

    public void update(T object) {

        createEntityManager();

        manager.merge(object);

        closeEntityManager();
    }

    public void delete(I id) {

        createEntityManager();

        manager.createQuery("UPDATE " + clazz.getSimpleName() + " SET deleted = true WHERE id = " + id).executeUpdate();

        closeEntityManager();
    }

    public T findById(I id) {

        createEntityManager();

        T object = manager.find(clazz, id);

        closeEntityManager();

        return object;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(int offset, int count) {

        createEntityManager();

        List<T> objects = (List<T>) manager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t WHERE deleted = 0")
                .setFirstResult(offset).setMaxResults(count).getResultList();

        closeEntityManager();

        return objects;
    }

    public Long countAll() {

        createEntityManager();

        Long count = (Long) manager.createQuery("SELECT COUNT(t) FROM " + clazz.getSimpleName() + " t WHERE deleted = 0").getSingleResult();

        closeEntityManager();

        return count;
    }

    private void createEntityManager() {
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    private void closeEntityManager() {
        manager.getTransaction().commit();
        manager.close();
    }
}