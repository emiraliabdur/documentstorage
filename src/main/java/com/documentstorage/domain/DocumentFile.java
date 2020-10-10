package com.documentstorage.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class DocumentFile {
    private String id;
    @Indexed
    private String path;
    private String subject;
    private String data;
}
