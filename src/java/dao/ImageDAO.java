/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Image;
import entity.Job;
import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import javax.persistence.EntityManager;
import utilities.DBUtility;

/**
 *
 * @author yncdb
 */
public class ImageDAO implements Serializable{
    
    public static int addImage(Image image) {
        EntityManager em = DBUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(image);
            em.getTransaction().commit();
            em.flush();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return image.getId();
    }
}
