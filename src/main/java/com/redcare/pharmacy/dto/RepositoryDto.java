package com.redcare.pharmacy.dto;

import java.time.LocalDate;

public record RepositoryDto(int limit, LocalDate fromDate, String language) {}
