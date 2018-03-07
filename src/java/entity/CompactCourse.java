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
@Table(name = "Course", catalog = "StudyIT", schema = "dbo")
@XmlRootElement(namespace = Page.courseNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class CompactCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    @XmlElement(namespace = Page.courseNamespace)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CourseName", nullable = false, length = 500)
    @XmlElement(namespace = Page.courseNamespace)
    private String courseName;
    @Column(name = "Thumbnail", length = 250)
    @XmlElement(namespace = Page.courseNamespace)
    private String thumbnail;
    @Basic(optional = false)
    @Column(name = "Tuition", nullable = false, length = 250)
    @XmlElement(namespace = Page.courseNamespace)
    private String tuition;
    @Column(name = "InstructorName", length = 250)
    @XmlElement(namespace = Page.courseNamespace)
    private String instructorName;

    public CompactCourse() {
    }

    public CompactCourse(Integer id, String courseName, String thumbnail, String tuition, String instructorName) {
        this.id = id;
        this.courseName = courseName;
        this.thumbnail = thumbnail;
        this.tuition = tuition;
        this.instructorName = instructorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
