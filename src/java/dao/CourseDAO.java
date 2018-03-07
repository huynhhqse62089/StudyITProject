/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.ArticleDAO.listArticleComp;
import entity.Article;
import entity.CompactArticle;
import entity.CompactCourse;
import entity.Course;
import entity.ListArticle;
import entity.ListCourse;
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
public class CourseDAO implements Serializable {

    static List<Course> listCourse = new ArrayList<>();

    static List<CompactCourse> listCourseComp = new ArrayList<>();

    public static int addCourse(Course course) {
        EntityManager em = DBUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
            em.flush();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return course.getId();
    }

    public static ListCourse get1kFilRecordCour() {
        EntityManager em = DBUtility.getEntityManager();
        ListCourse list = new ListCourse();
        try {
            Query query = em.createQuery("Select a.id, "
                    + "a.courseName, "
                    + "a.thumbnail, "
                    + "a.tuition, "
                    + "a.instructorName "
                    + "FROM Course a");
            List<Object[]> rows = query.getResultList();
            listCourseComp = new ArrayList<>(rows.size());
            for (Object[] row : rows) {               
                listCourseComp.add( new CompactCourse((int)row[0],
                        (String)row[1], (String)row[2], (String)row[3], (String)row[4]));
            }
            list.setCourseList(listCourseComp);

        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return list;
    }
}
