package com.bcmworld.tp1.model.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class GenericDAO<T, I> {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("HibernatePersistence");
    protected EntityManager manager;

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

    public void delete(Class<T> clazz, I id) {

        createEntityManager();

        manager.createQuery("UPDATE " + clazz.getSimpleName() + " SET deleted = true WHERE id = " + id).executeUpdate();

        closeEntityManager();
    }

    public T findById(Class<T> clazz, I id) {

        createEntityManager();

        T object = manager.find(clazz, id);

        closeEntityManager();

        return object;
    }

    public List<T> findAll(Class<T> clazz) {

        createEntityManager();

        List<T> objects = (List<T>) manager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t").getResultList();

        closeEntityManager();

        return objects;
    }

    protected void createEntityManager() {
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    protected void closeEntityManager() {
        manager.getTransaction().commit();
        manager.close();
    }

    public void close() {
        factory.close();
    }
}