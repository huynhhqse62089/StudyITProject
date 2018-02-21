/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author yncdb
 */
public class DBUtility implements Serializable {

    private static EntityManagerFactory emf;
    EntityManager em = null;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("StudyITWebSitePU");
        } catch (Exception e) {
            log(DBUtility.class.getName() + " Exception: " + e.getMessage());
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }      
}
