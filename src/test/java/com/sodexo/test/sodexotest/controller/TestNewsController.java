package com.sodexo.test.sodexotest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sodexo.test.sodexotest.model.News;
import com.sodexo.test.sodexotest.service.NewsService;

public class TestNewsController {

    private NewsController newsController;
    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        newsService = mock(NewsService.class);
        newsController = new NewsController(newsService);
    }

    //add
    @Test
    public void testAddToFavoritesSuccess() {
        News newNews = new News();
        newNews.setId(123);
        when(newsService.findById(123)).thenReturn(Optional.empty());
        ResponseEntity<?> response = newsController.addToFavorites(newNews);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void testAddToFavoritesError() {
        News newNews = new News();
        newNews.setId(123123);
        when(newsService.findById(123123)).thenReturn(Optional.empty());
        when(newsService.addToFavorite(newNews)).thenThrow(new RuntimeException("Error adding new"));

        ResponseEntity<?> response = newsController.addToFavorites(newNews);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //delete
    @Test
    public void testDeleteFavoriteSuccess() {
        ResponseEntity<?> response = newsController.deleteFavorite(123456);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteFavoriteError() {
        doThrow(new RuntimeException("Error deleting new")).when(newsService).deleteFavorite(23432);
        ResponseEntity<?> response = newsController.deleteFavorite(23432);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //get 
    @Test
    public void testGetFavoritesSuccess() {
        List<News> favoriteNews = new ArrayList<>();

        News news1 = new News();
        news1.setId(21010);
        news1.setTitle("NASA delays Psyche launch a week");
        news1.setUrl("https://spacenews.com/nasa-delays-psyche-launch-a-week/");
        news1.setNews_site("SpaceNews");
        news1.setSummary(
                "NASA has postponed the launch of the asteroid mission Psyche a week to update the configuration of thrusters on the spacecraft.");
        news1.setPublished_at("2023-09-29T02:50:16Z");

        favoriteNews.add(news1);

        when(newsService.getFavorites()).thenReturn(favoriteNews);

        ResponseEntity<?> response = newsController.getFavorites();

        assert (response.getStatusCode() == HttpStatus.OK);
        assert (response.getBody() instanceof List);
        List<?> responseList = (List<?>) response.getBody();
        assert (responseList.size() == favoriteNews.size());
    }

    @Test
    public void testGetFavoritesNoContent() {
        List<News> favoriteNews = new ArrayList<>();
        when(newsService.getFavorites()).thenReturn(favoriteNews);
        ResponseEntity<?> response = newsController.getFavorites();

        assert (response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetFavoritesError() {
        when(newsService.getFavorites()).thenThrow(new RuntimeException("Error getting news"));
        ResponseEntity<?> response = newsController.getFavorites();

        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
