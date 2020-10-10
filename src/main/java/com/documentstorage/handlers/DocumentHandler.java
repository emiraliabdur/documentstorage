package com.documentstorage.handlers;

import com.documentstorage.domain.DocumentFile;
import com.documentstorage.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DocumentHandler {

    @Autowired
    private DocumentService documentService;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String documentId = serverRequest.pathVariable("id");
        Flux<DocumentFile> documentFileFlux = this.documentService.findWithDescendants(documentId);
        return ServerResponse.ok().body(documentFileFlux, DocumentFile.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<DocumentFile> documentFileMono = serverRequest.bodyToMono(DocumentFile.class);
        return ServerResponse.ok()
                .body(documentFileMono.flatMap(this.documentService::save),
                        DocumentFile.class);
    }
}
