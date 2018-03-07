/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import page.Page;

/**
 *
 * @author yncdb
 */
@XmlRootElement(name = "courses", namespace = Page.courseNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listCourse", propOrder = {
    "course"
})
public class ListCourse {
    
    @XmlElement(namespace = Page.courseNamespace)
    private List<CompactCourse> course;

    public List<CompactCourse> getCourseList() {
        if(course == null){
            course = new ArrayList<CompactCourse>();
        }
        return course;
    }

    public void setCourseList(List<CompactCourse> course) {
        this.course = course;
    }
      
}
