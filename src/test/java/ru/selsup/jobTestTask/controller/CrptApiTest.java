package ru.selsup.jobTestTask.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.selsup.jobTestTask.model.Document;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class CrptApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createDocument() throws IOException {
        String postHttpRequestJSON = getTestPostHttpRequestJSON();

        Document returnedReader = webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/lk/documents/create")
                        .queryParam("sign", "test_signature")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(postHttpRequestJSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Document.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(returnedReader);
    }

    private String getTestPostHttpRequestJSON() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/documentPostRequestJSON.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }
}