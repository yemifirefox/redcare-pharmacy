package com.redcare.pharmacy.adapter;

import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;
import com.redcare.pharmacy.exception.RemoteSystemException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubClient implements GithubPort{

    private final RestClient restClient;

    public GithubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Repository.Item> getPopularRepositories(RepositoryDto repositoryDto) {
        String query = getQuery(repositoryDto);

        var result = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/repositories")
                        .queryParam("q", query.isEmpty() ? "all" : query)
                        .queryParam("per_page", repositoryDto.limit())
                        .queryParam("sort", "stars")
                        .queryParam("order", "desc")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RemoteSystemException(HttpStatus.NOT_FOUND, "Unable to retrieve repositories");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RemoteSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
                })
                .body(Repository.class);

        if (result == null || result.items().isEmpty()) {
            throw new RemoteSystemException(HttpStatus.NOT_FOUND, "No repositories found");
        }
        return result.items();
    }

    private static String getQuery(RepositoryDto repositoryDto) {
        String fromDateQuery = repositoryDto.fromDate() != null ? String.format("created:>=%s+", repositoryDto.fromDate()) : "";
        String languageQuery = repositoryDto.language() != null ? String.format("language:%s", repositoryDto.language()) : "";
        return String.format("%s%s", fromDateQuery, languageQuery);
    }
}
