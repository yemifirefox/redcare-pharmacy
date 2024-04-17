package com.redcare.pharmacy.adapter;

import com.redcare.pharmacy.dto.RepositoryDto;
import com.redcare.pharmacy.exception.RemoteSystemException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import util.AbstractMockWebServerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GithubClientTest extends AbstractMockWebServerTest {

    private GithubClient client;

    @BeforeEach
    void setup() throws IOException {
        super.beforeEach();
        client = new GithubClient(gerRestClient());
    }

    @AfterEach
    void cleanUp() throws IOException {
        super.afterEach();
    }

    @Test
    void returnTwoRepositories() throws IOException {
        final var json = new ClassPathResource("response.json").getContentAsString(StandardCharsets.UTF_8);
        mockBackEndEnqueueJsonStringBody(200, json);

        final var result = client.getPopularRepositories(new RepositoryDto(10, LocalDate.parse("2024-04-16"), "java"));

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    void andThrow404NotFound() {
        var expectedErrorMessage = "Unable to retrieve repositories";

        mockBackEndEnqueueJsonNoBody(404);

        var request = new RepositoryDto(10, LocalDate.parse("2024-04-16"), "java");

        var exception = assertThrows(RemoteSystemException.class, () -> client.getPopularRepositories(request));

        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void andThrow500Error() {
        mockBackEndEnqueueJsonNoBody(
                500);
        var request = new RepositoryDto(10, LocalDate.parse("2024-04-16"), "java");
        var exception = assertThrows(RemoteSystemException.class, () -> client.getPopularRepositories(request));

        var expectedErrorMessage = "Server error";
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
