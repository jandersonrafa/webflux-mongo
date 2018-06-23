package com.article.article.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.article.article.model.Article;

/**
 *
 * @author Janderson
 */
@Repository
public interface ArticleSimpleRepository extends MongoRepository<Article, String> {

	List<Article> findByEvent_EventId(String eventId);
}
