/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.CourseDAO.listCourseComp;
import entity.Article;
import entity.CompactCourse;
import entity.CompactJob;
import entity.Course;
import entity.Job;
import entity.ListCourse;
import entity.ListJob;
import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utilities.DBUtility;

/**
 *
 * @author yncdb
 */
public class JobDAO implements Serializable{
    
    static List<Job> listJob = new ArrayList<>();

    static List<CompactJob> listJobComp = new ArrayList<>();
    
    public static int addJob(Job job) {
        EntityManager em = DBUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(job);
            em.getTransaction().commit();
            em.flush();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return job.getId();
    }
    
    public static ListJob get1kFilRecordJob() {
        EntityManager em = DBUtility.getEntityManager();
        ListJob list = new ListJob();
        try {
            Query query = em.createQuery("Select a.id, "
                    + "a.companyName, "
                    + "a.title, "
                    + "a.pubDate, "
                    + "a.summary, "
                    + "a.thumbnail "
                    + "FROM Job a");
            query.setMaxResults(500);
            List<Object[]> rows = query.getResultList();
            listJobComp = new ArrayList<>(rows.size());
            for (Object[] row : rows) {               
                listJobComp.add( new CompactJob((int)row[0],
                        (String)row[1], (String)row[2], (String)row[3], 
                        (String)row[4], (String)row[5]));
            }
            list.setJobList(listJobComp);

        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return list;
    }
}
