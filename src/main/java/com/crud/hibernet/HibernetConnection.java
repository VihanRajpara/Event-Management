package com.crud.hibernet;

import org.hibernate.boot.Metadata;
import java.util.Map;
import com.crud.dao.hod;
import com.crud.dao.faculty;
import com.crud.dao.dep;
import com.crud.dao.event;
import com.crud.dao.student;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.MetadataSources;
import java.util.HashMap;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

public class HibernetConnection {
	private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (HibernetConnection.sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Map settings = new HashMap();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3307/event");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");
                registryBuilder.applySettings(settings);
                HibernetConnection.registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources((ServiceRegistry)HibernetConnection.registry);
                sources.addAnnotatedClass((Class)hod.class);
                sources.addAnnotatedClass((Class)faculty.class);
                sources.addAnnotatedClass((Class)dep.class);
                sources.addAnnotatedClass(student.class);
                sources.addAnnotatedClass((Class)event.class);
                
               Metadata metadata = sources.getMetadataBuilder().build();
                HibernetConnection.sessionFactory = metadata.getSessionFactoryBuilder().build();
            }
            catch (Exception e) {
                System.out.println("SessionFactory creation failed-----------------------------------"+e+"----------------------------");
                if (HibernetConnection.registry != null) {
                    StandardServiceRegistryBuilder.destroy((ServiceRegistry)HibernetConnection.registry);
                }
            }
        }
        return HibernetConnection.sessionFactory;
    }
    
    public static void shutdown() {
        if (HibernetConnection.registry != null) {
            StandardServiceRegistryBuilder.destroy((ServiceRegistry)HibernetConnection.registry);
        }
    }
}
