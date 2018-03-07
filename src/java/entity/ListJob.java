/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
@XmlRootElement(name = "jobs", namespace = Page.jobNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listJob", propOrder = {
    "job"
})
public class ListJob {
    
    @XmlElement(namespace = Page.jobNamespace)
    private List<CompactJob> job;

    public List<CompactJob> getJobList() {
        return job;
    }

    public void setJobList(List<CompactJob> job) {
        this.job = job;
    }
   
}
