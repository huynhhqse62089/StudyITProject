/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yncdb
 */
@Entity
@Table(name = "Article", catalog = "StudyIT", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
    , @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id")
    , @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title")
    , @NamedQuery(name = "Article.findByLink", query = "SELECT a FROM Article a WHERE a.link = :link")
    , @NamedQuery(name = "Article.findByDescription", query = "SELECT a FROM Article a WHERE a.description = :description")
    , @NamedQuery(name = "Article.findByThumbnail", query = "SELECT a FROM Article a WHERE a.thumbnail = :thumbnail")
    , @NamedQuery(name = "Article.findBySummary", query = "SELECT a FROM Article a WHERE a.summary = :summary")
    , @NamedQuery(name = "Article.findByAuthor", query = "SELECT a FROM Article a WHERE a.author = :author")
    , @NamedQuery(name = "Article.findByPubDate", query = "SELECT a FROM Article a WHERE a.pubDate = :pubDate")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 500)
    private String title;
    @Column(name = "Link", length = 200)
    private String link;
    @Column(name = "Description", length = 2147483647)
    private String description;
    @Column(name = "Thumbnail", length = 200)
    private String thumbnail;
    @Column(name = "Summary", length = 500)
    private String summary;
    @Column(name = "Author", length = 200)
    private String author;
    @Column(name = "PubDate", length = 100)
    private String pubDate;
    @OneToMany(mappedBy = "articleId")
    private Collection<Image> imageCollection;

    public Article() {
    }

    public Article(Integer id) {
        this.id = id;
    }

    public Article(Integer id, String title) {
        this.id = id;
        this.title = title;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @XmlTransient
    public Collection<Image> getImageCollection() {
        return imageCollection;
    }

    public void setImageCollection(Collection<Image> imageCollection) {
        this.imageCollection = imageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Article[ id=" + id + " ]";
    }
    
}
