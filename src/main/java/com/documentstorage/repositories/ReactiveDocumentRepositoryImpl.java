package com.documentstorage.repositories;

import com.documentstorage.domain.DocumentFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class ReactiveDocumentRepositoryImpl implements ReactiveDocumentRepository {

    private static final String PATH_SEARCH_QUERY = "{ path: /^%s/ }";

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private MongoTemplate mongoTemplate2;

    @Override
    public Mono<DocumentFile> save(DocumentFile documentFile) {
        return mongoTemplate.save(documentFile);
    }

    @Override
    public Mono<DocumentFile> findById(String documentId) {
        return mongoTemplate.findById(documentId, DocumentFile.class);
    }

    @Override
    public Flux<DocumentFile> findByPath(String searchPath) {
        return mongoTemplate.find(new BasicQuery(String.format(PATH_SEARCH_QUERY, searchPath)), DocumentFile.class);
    }
}
