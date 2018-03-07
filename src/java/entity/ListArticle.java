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
@XmlRootElement(name = "articles")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listArticle", propOrder = {
    "article"
})
public class ListArticle {
    
    @XmlElement(required = true)
    private List<CompactArticle> article;

    public List<CompactArticle> getArticle() {
        if(article == null){
            article = new ArrayList<CompactArticle>();
        }
        return article;
    }

    public void setArticle(List<CompactArticle> article) {
        this.article = article;
    }
       
}
