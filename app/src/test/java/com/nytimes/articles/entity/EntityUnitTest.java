package com.nytimes.articles.entity;

import com.nytimes.articles.data.local.entity.ArticleEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EntityUnitTest {

    @Test
    public void testId(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(1000);
        assertEquals(articleEntity.getId(), 1000);
    }

    @Test
    public void testTitle(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("test");
        assertEquals(articleEntity.getTitle(), "test");
    }

    @Test
    public void testContent(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setContent("test");
        assertEquals(articleEntity.getContent(), "test");
    }

    @Test
    public void testPublishDate(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setPublishedDate("test");
        assertEquals(articleEntity.getPublishedDate(), "test");
    }

    @Test
    public void testAuthors(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthors("test");
        assertEquals(articleEntity.getAuthors(), "test");
    }
}
