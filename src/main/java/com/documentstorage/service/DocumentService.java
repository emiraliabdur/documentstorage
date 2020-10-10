package com.documentstorage.service;

import com.documentstorage.domain.DocumentFile;
import com.documentstorage.repositories.ReactiveDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DocumentService {

    @Autowired
    private ReactiveDocumentRepository documentRepository;

    @Value("${document.pathSeparator}")
    private Character pathSeparator;

    public Mono<DocumentFile> save(DocumentFile documentFile) {
        documentFile.setPath(StringUtils.trimTrailingCharacter(documentFile.getPath(), pathSeparator));

        log.info("Saving document: '{}' into '{}'", documentFile.getSubject(), documentFile.getPath());
        return documentRepository.save(documentFile);
    }

    public Flux<DocumentFile> findWithDescendants(String documentId) {
        log.info("Getting document: '{}'", documentId);
        return documentRepository.findById(documentId)
                .flatMapMany(rootDocument ->
                        Mono.just(rootDocument)
                                .mergeWith(documentRepository.findByPath(rootDocument.getPath() + pathSeparator))
        );
    }
}