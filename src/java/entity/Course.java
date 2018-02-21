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
@Table(name = "Course", catalog = "StudyIT", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
    , @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id")
    , @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName")
    , @NamedQuery(name = "Course.findByTimeStudy", query = "SELECT c FROM Course c WHERE c.timeStudy = :timeStudy")
    , @NamedQuery(name = "Course.findByOpeningDate", query = "SELECT c FROM Course c WHERE c.openingDate = :openingDate")
    , @NamedQuery(name = "Course.findByLocation", query = "SELECT c FROM Course c WHERE c.location = :location")
    , @NamedQuery(name = "Course.findByLink", query = "SELECT c FROM Course c WHERE c.link = :link")
    , @NamedQuery(name = "Course.findByTuition", query = "SELECT c FROM Course c WHERE c.tuition = :tuition")
    , @NamedQuery(name = "Course.findByInstructorName", query = "SELECT c FROM Course c WHERE c.instructorName = :instructorName")
    , @NamedQuery(name = "Course.findByLengthStudy", query = "SELECT c FROM Course c WHERE c.lengthStudy = :lengthStudy")
    , @NamedQuery(name = "Course.findByThumbnail", query = "SELECT c FROM Course c WHERE c.thumbnail = :thumbnail")
    , @NamedQuery(name = "Course.findByDescription", query = "SELECT c FROM Course c WHERE c.description = :description")})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CourseName", nullable = false, length = 500)
    private String courseName;
    @Column(name = "TimeStudy", length = 500)
    private String timeStudy;
    @Column(name = "OpeningDate", length = 250)
    private String openingDate;
    @Column(name = "Location", length = 250)
    private String location;
    @Column(name = "Link", length = 200)
    private String link;
    @Basic(optional = false)
    @Column(name = "Tuition", nullable = false, length = 250)
    private String tuition;
    @Column(name = "InstructorName", length = 250)
    private String instructorName;
    @Basic(optional = false)
    @Column(name = "LengthStudy", nullable = false, length = 250)
    private String lengthStudy;
    @Column(name = "Thumbnail", length = 250)
    private String thumbnail;
    @Column(name = "Description", length = 2147483647)
    private String description;
    @OneToMany(mappedBy = "courseId")
    private Collection<Image> imageCollection;

    public Course() {
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course(Integer id, String courseName, String tuition, String lengthStudy) {
        this.id = id;
        this.courseName = courseName;
        this.tuition = tuition;
        this.lengthStudy = lengthStudy;
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

    public String getTimeStudy() {
        return timeStudy;
    }

    public void setTimeStudy(String timeStudy) {
        this.timeStudy = timeStudy;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getLengthStudy() {
        return lengthStudy;
    }

    public void setLengthStudy(String lengthStudy) {
        this.lengthStudy = lengthStudy;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Course[ id=" + id + " ]";
    }
    
}
