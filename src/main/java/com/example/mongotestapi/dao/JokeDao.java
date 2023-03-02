package com.example.mongotestapi.dao;



import com.example.mongotestapi.domain.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Repository;

/**
 * User: 59157
 * Date: 2020/12/8
 * Time: 14:52
 */
@Repository
public class JokeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Joke joke) {

        mongoTemplate.save(joke);
    }

}


