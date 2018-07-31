package cn.yangtengfei.model.sourceCode;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;


@Document(collection = "sourceCode")
public class SourceCodeModel implements Serializable{

    private String id;

    private String link;

    private String name;

    private int age;

    private Date date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
