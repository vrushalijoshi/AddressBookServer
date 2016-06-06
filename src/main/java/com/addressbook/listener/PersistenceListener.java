package com.addressbook.listener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener class creates entity manager factory based on the configured
 * persistence unit
 * 
 * @author Vrushali
 *
 */
public class PersistenceListener implements ServletContextListener {

	private static EntityManagerFactory entityManagerFactory;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		entityManagerFactory = Persistence.createEntityManagerFactory("addressbook");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		entityManagerFactory.close();
	}

	/**
	 * Gets entity manager from the entity manager factory
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		if (entityManagerFactory == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * @param entityManagerFactory
	 *            the entityManagerFactory to set
	 */
	public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		PersistenceListener.entityManagerFactory = entityManagerFactory;
	}
}