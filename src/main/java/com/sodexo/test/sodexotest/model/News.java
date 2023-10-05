package com.sodexo.test.sodexotest.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News {
    @Id
    @Column(unique = true)
    private Integer id;
    private String title;
    private String url;
    private String news_site;
    private String summary;
    private String published_at;
    
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Timestamp added_at;


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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getNews_site() {
        return news_site;
    }
    public void setNews_site(String news_site) {
        this.news_site = news_site;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    @PrePersist
    @PreUpdate
    public void truncateSummary() {
        if (summary != null && summary.length() > 255) {
            summary = summary.substring(0, 255);
        }
    }
    public String getPublished_at() {
        return published_at;
    }
    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }
    public Timestamp getAdded_at() {
        return added_at;
    }
    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
    
}
