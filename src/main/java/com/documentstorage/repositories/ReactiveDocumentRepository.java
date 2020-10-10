package com.documentstorage.repositories;

import com.documentstorage.domain.DocumentFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveDocumentRepository {
    Mono<DocumentFile> save(DocumentFile documentFile);

    Mono<DocumentFile> findById(String documentId);

    Flux<DocumentFile> findByPath(String path);
}
