package com.documentstorage.handlers;

import com.documentstorage.WebConfig;
import com.documentstorage.domain.DocumentFile;
import com.documentstorage.service.DocumentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebConfig.class, DocumentHandler.class})
@WebFluxTest
public class DocumentHandlerTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private DocumentService documentService;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void shouldSaveDocument() {

        DocumentFile df = buildDocumentFile("root", null);

        Mono<DocumentFile> documentFileMono = Mono.just(df);
        when(documentService.save(df))
                .thenReturn(documentFileMono.map(documentFile -> {
                    documentFile.setId(RandomStringUtils.randomAlphabetic(5));
                    return documentFile;
                }));

        webTestClient.post()
                .uri("/documents")
                .body(Mono.just(df), DocumentFile.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DocumentFile.class)
                .value(documentFileResponse -> {
                            assertThat(documentFileResponse.getId()).isNotEmpty();
                            assertThat(documentFileResponse.getPath()).isEqualTo(df.getPath());
                            assertThat(documentFileResponse.getSubject()).isEqualTo(df.getSubject());
                });
    }

    @Test
    public void shouldGetDocument() {
        DocumentFile rootDf = buildDocumentFile("root", "00");
        DocumentFile childDf = buildDocumentFile("root.child", "11");

        when(documentService.findWithDescendants("00")).thenReturn(Flux.just(rootDf, childDf));

        webTestClient.get()
                .uri("/documents/00")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DocumentFile.class)
                .value(documentFilesResponse -> {
                    assertThat(documentFilesResponse).hasSize(2);

                    assertDocumentFile(rootDf, documentFilesResponse.get(0));
                    assertDocumentFile(childDf, documentFilesResponse.get(1));
                });
    }

    private void assertDocumentFile(DocumentFile expected, DocumentFile actual) {
        assertThat(actual.getPath()).isEqualTo(expected.getPath());
        assertThat(actual.getSubject()).isEqualTo(expected.getSubject());
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    private DocumentFile buildDocumentFile(String path, String id) {
        DocumentFile df = new DocumentFile();
        df.setId(id);
        df.setPath(path);
        df.setSubject("subject");
        df.setData("text");

        return df;
    }
}