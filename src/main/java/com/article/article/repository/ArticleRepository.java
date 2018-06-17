package com.article.article.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.article.article.model.Article;

/**
 *
 * @author Janderson
 */
@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {

}
