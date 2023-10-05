package com.sodexo.test.sodexotest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sodexo.test.sodexotest.model.News;
import com.sodexo.test.sodexotest.repository.NewsRepository;

@Component
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public Optional<News> findById(Integer id) {
        return newsRepository.findById(id);
    }

    public News addToFavorite(News news) {
        return newsRepository.save(news);
    }

    public void deleteFavorite(Integer id) {
        newsRepository.deleteById(id);
    }

    public List<News> getFavorites() {
        return newsRepository.findAll();
    }

    public List<News> findByTitle(String keyword) {
        return newsRepository.findByTitle(keyword);
    }

    public List<News> sortByTitle() {
        return newsRepository.sortByTitle();
    }

    public List<News> sortBySite() {
        return newsRepository.sortBySite();
    }

    public List<News> sortByPublishedTime() {
        return newsRepository.sortByPublishedTime();
    }

    public List<News> sortByAddedTime() {
        return newsRepository.sortByAddedTime();
    }

}
