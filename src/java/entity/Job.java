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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "Job", catalog = "StudyIT", schema = "dbo")
@XmlRootElement(namespace = Page.jobNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
    , @NamedQuery(name = "Job.findById", query = "SELECT j FROM Job j WHERE j.id = :id")
    , @NamedQuery(name = "Job.findByCompanyName", query = "SELECT j FROM Job j WHERE j.companyName = :companyName")
    , @NamedQuery(name = "Job.findByTitle", query = "SELECT j FROM Job j WHERE j.title = :title")
    , @NamedQuery(name = "Job.findByPubDate", query = "SELECT j FROM Job j WHERE j.pubDate = :pubDate")
    , @NamedQuery(name = "Job.findByDescription", query = "SELECT j FROM Job j WHERE j.description = :description")
    , @NamedQuery(name = "Job.findBySummary", query = "SELECT j FROM Job j WHERE j.summary = :summary")
    , @NamedQuery(name = "Job.findByThumbnail", query = "SELECT j FROM Job j WHERE j.thumbnail = :thumbnail")})
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    @XmlElement(namespace = Page.jobNamespace)
    private Integer id;
    @Column(name = "CompanyName", length = 250)
    @XmlElement(namespace = Page.jobNamespace)
    private String companyName;
    @Column(name = "Title", length = 250)
    @XmlElement(namespace = Page.jobNamespace)
    private String title;
    @Column(name = "PubDate", length = 250)
    @XmlElement(namespace = Page.jobNamespace)
    private String pubDate;
    @Column(name = "Description", length = 2147483647)
    @XmlElement(namespace = Page.jobNamespace)
    private String description;
    @Column(name = "Summary", length = 500)
    @XmlElement(namespace = Page.jobNamespace)
    private String summary;
    @Column(name = "Thumbnail", length = 200)
    @XmlElement(namespace = Page.jobNamespace)
    private String thumbnail;
    @Column(name = "Link", length = 200)
    @XmlElement(namespace = Page.jobNamespace)
    private String link;

    public Job() {
    }

    public Job(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Job[ id=" + id + " ]";
    }

}
