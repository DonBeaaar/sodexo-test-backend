package com.sodexo.test.sodexotest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sodexo.test.sodexotest.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News,Integer> {
    @Query("SELECT n FROM News n WHERE n.title LIKE %:keyword%")
    List<News> findByTitle(@Param("keyword") String keyword);

    @Query("SELECT n FROM News n ORDER BY n.title")
    List<News> sortByTitle();

    @Query("SELECT n FROM News n ORDER BY n.news_site")
    List<News> sortBySite();

    @Query("SELECT n FROM News n ORDER BY n.published_at")
    List<News> sortByPublishedTime();

    @Query("SELECT n FROM News n ORDER BY n.added_at")
    List<News> sortByAddedTime();

}
