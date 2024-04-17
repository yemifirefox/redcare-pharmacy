package com.redcare.pharmacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Repository(@JsonProperty("total_count") int totalCount,
                         @JsonProperty("incomplete_results") boolean incompleteResults,
                         @JsonProperty("items") List<Item> items) {

    public record Item(Integer id, String name, @JsonProperty("full_name") String fullName,
                @JsonProperty("private") boolean private_, @JsonProperty("html_url") String htmlUrl,
                       @JsonProperty("git_url") String gitUrl, String language,
                       @JsonProperty("created_at") String createdAt, @JsonProperty("stargazers_count") String stargazersCount) {

    }
}
