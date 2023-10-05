package com.sodexo.test.sodexotest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sodexo.test.sodexotest.model.News;
import com.sodexo.test.sodexotest.service.NewsService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<?> addToFavorites(@RequestBody News news) {
        
        // Check if exists before save
        Integer id = news.getId();
        Optional<News> existingNew = newsService.findById(id);
        if (existingNew.isPresent()) {
            return new ResponseEntity<>(existingNew,HttpStatus.OK);
        }

        try {
            News addedNew = this.newsService.addToFavorite(news);
            return new ResponseEntity<>(addedNew, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error adding new to favorites: " + e);
            return new ResponseEntity<>("Error adding new to favorites.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable("id") Integer id) {
        try {
            this.newsService.deleteFavorite(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Error deleting new from favorites: " + e);
            return new ResponseEntity<>("Error deleting new from favorites.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getFavorites() {
        try {
            List<News> news = this.newsService.getFavorites();
            if (news.isEmpty()) {
                return new ResponseEntity<>("Not found news", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error finding getting favorite news: " + e);
            return new ResponseEntity<>("Error getting favorite news.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchNewsByTitle(@RequestParam("title") String keyword) {
        try {
            List<News> news = newsService.findByTitle(keyword);
            if (news.isEmpty()) {
                return new ResponseEntity<>("Not found by title news", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error finding news by title: " + e);
            return new ResponseEntity<>("Error finding news by title.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort/title")
    public ResponseEntity<?> sortByTitle() {
        try {
            List<News> sortedNews = newsService.sortByTitle();
            return new ResponseEntity<>(sortedNews, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error sorting news by title: " + e);
            return new ResponseEntity<>("Error sorting news by title.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort/site")
    public ResponseEntity<?> sortBySite() {
        try {
            List<News> sortedNews = newsService.sortBySite();
            return new ResponseEntity<>(sortedNews, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error sorting news by site: " + e);
            return new ResponseEntity<>("Error sorting news by site.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort/published")
    public ResponseEntity<?> sortByPublishedTime() {
        try {
            List<News> sortedNews = newsService.sortByPublishedTime();
            return new ResponseEntity<>(sortedNews, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error sorting news by published time: " + e);
            return new ResponseEntity<>("Error sorting news by published time.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort/added")
    public ResponseEntity<?> sortByAddedTime() {
        try {
            List<News> sortedNews = newsService.sortByAddedTime();
            return new ResponseEntity<>(sortedNews, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error sorting news by added time: " + e);
            return new ResponseEntity<>("Error sorting news by added time.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}