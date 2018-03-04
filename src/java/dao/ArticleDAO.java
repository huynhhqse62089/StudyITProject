/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Article;
import entity.Course;
import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utilities.DBUtility;

/**
 *
 * @author yncdb
 */
public class ArticleDAO implements Serializable {

    static List<Article> listArticle = new ArrayList<>();

    public static List<Article> getListArticle() {
        return listArticle;
    }

    public static int addArticle(Article article) {
        EntityManager em = DBUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(article);
            em.getTransaction().commit();
            em.flush();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return article.getId();
    }

    public static List<Article> getAllArticle() {
        EntityManager em = DBUtility.getEntityManager();
        try {
            TypedQuery<Article> query = em.createNamedQuery(
                    "Article.findAll", Article.class);
            listArticle = query.getResultList();
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return listArticle;
    }
}
