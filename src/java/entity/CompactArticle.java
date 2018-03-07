/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import page.Page;

/**
 *
 * @author yncdb
 */
@Entity
@Table(name = "Article", catalog = "StudyIT", schema = "dbo")
@XmlRootElement(namespace = Page.articleNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class CompactArticle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    @XmlElement(namespace = Page.articleNamespace)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 500)
    @XmlElement(namespace = Page.articleNamespace)
    private String title;
    @Column(name = "Thumbnail", length = 200)
    @XmlElement(namespace = Page.articleNamespace)
    private String thumbnail;
    @Column(name = "Summary", length = 500)
    @XmlElement(namespace = Page.articleNamespace)
    private String summary;
    @Column(name = "PubDate", length = 100)
    @XmlElement(namespace = Page.articleNamespace)
    private String pubDate;

    public CompactArticle() {
    }

    public CompactArticle(Integer id, String title, String thumbnail, String summary, String pubDate) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.summary = summary;
        this.pubDate = pubDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
