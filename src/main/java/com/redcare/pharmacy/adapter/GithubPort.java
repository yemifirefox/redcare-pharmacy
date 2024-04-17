package com.redcare.pharmacy.adapter;

import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;

import java.util.List;

public interface GithubPort {
    List<Repository.Item> getPopularRepositories(RepositoryDto repositoryDto);
}
