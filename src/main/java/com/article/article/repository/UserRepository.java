package com.article.article.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.article.article.model.User;

/**
 *
 * @author Janderson
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, BigInteger> {

}
