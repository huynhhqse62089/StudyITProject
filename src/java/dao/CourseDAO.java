/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Course;
import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import javax.persistence.EntityManager;
import utilities.DBUtility;

/**
 *
 * @author yncdb
 */
public class CourseDAO implements Serializable {

    public static int addCourse(Course course) {
        EntityManager em = DBUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return course.getId();
    }
}
