/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Article;
import entity.CompactArticle;
import entity.Course;
import entity.ListArticle;
import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import utilities.DBUtility;

/**
 *
 * @author yncdb
 */
public class ArticleDAO implements Serializable {

    static List<Article> listArticle = new ArrayList<>();

    static List<CompactArticle> listArticleComp = new ArrayList<>();

    public static List<Article> getListArticle() {
        return listArticle;
    }

    public static List<CompactArticle> getListArticleComp() {
        return listArticleComp;
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

    public static ListArticle getNFilRecordArt(int numberRecord) {
        EntityManager em = DBUtility.getEntityManager();
        ListArticle list = new ListArticle();
        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery q = cb.createQuery(Article.class);
//            Root<Article> c = q.from(Article.class);
//            q.multiselect(
//                    c.get("id"), c.get("title"), c.get("thumbnail"), c.get("summary"));
//            ((org.eclipse.persistence.jpa.JpaQuery) q).getDatabaseQuery().dontMaintainCache();
            Query query = em.createQuery("Select a.id, "
                    + "a.title, "
                    + "a.thumbnail, "
                    + "a.summary,"
                    + "a.pubDate "
                    + "FROM Article a "
                    + "ORDER BY a.pubDate DESC");
//            Query query = em.createQuery("Select a FROM Article a");
            query.setMaxResults(numberRecord);
            List<Object[]> rows = query.getResultList();
            listArticleComp = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                listArticleComp.add(new CompactArticle((int) row[0],
                        (String) row[1], (String) row[2], (String) row[3], (String) row[4]));
            }
            list.setArticle(listArticleComp);

        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            em.close();
        }
        return list;
    }
}
