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
@Table(name = "Job", catalog = "StudyIT", schema = "dbo")
@XmlRootElement(namespace = Page.jobNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class CompactJob implements Serializable {

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
    @Column(name = "Summary", length = 500)
    @XmlElement(namespace = Page.jobNamespace)
    private String summary;
    @Column(name = "Thumbnail", length = 200)
    @XmlElement(namespace = Page.jobNamespace)
    private String thumbnail;

    public CompactJob() {
    }

    public CompactJob(Integer id, String companyName, String title, String pubDate, String summary, String thumbnail) {
        this.id = id;
        this.companyName = companyName;
        this.title = title;
        this.pubDate = pubDate;
        this.summary = summary;
        this.thumbnail = thumbnail;
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
    
    
}
