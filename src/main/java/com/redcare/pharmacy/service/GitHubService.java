package com.redcare.pharmacy.service;

import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;

import java.util.List;

public interface GitHubService {
    List<Repository.Item> getPopularRepositories(RepositoryDto repositoryDto);
}
