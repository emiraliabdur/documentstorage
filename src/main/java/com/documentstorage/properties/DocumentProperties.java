package com.documentstorage.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "document")
@Getter
public class DocumentProperties {
    private String pathSeparator;
}
