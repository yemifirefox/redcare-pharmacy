package com.redcare.pharmacy.controller;

import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;
import com.redcare.pharmacy.service.GitHubService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestData.buildResponse;

@WebMvcTest(controllers = RepositoryController.class)
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;


    @Test
    void getPopularRepositories() throws Exception {
        var date  = LocalDate.now().toString();
        var expected = buildResponse(date);
        when(gitHubService.getPopularRepositories(any(RepositoryDto.class))).thenReturn(expected);

        assertPopularRepositories(date, expected);

        verify(gitHubService, times(1)).getPopularRepositories(any());
      }

    private void assertPopularRepositories(String date, List<Repository.Item> expected) throws Exception {
        var mockMvcResult = this.mockMvc
                .perform(
                        get("/repositories/popular")
                                .param("limit", "10")
                                .param("created_at", date)
                                .param("language", "java"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < expected.size(); i++) {
            var item = expected.get(i);
            var jsonPath = "$.[" + i + "]";

            mockMvcResult
                    .andExpect(jsonPath(jsonPath + ".id").value(item.id()))
                    .andExpect(jsonPath(jsonPath + ".name").value(item.name()))
                    .andExpect(jsonPath(jsonPath + ".full_name").value(item.fullName()))
                    .andExpect(jsonPath(jsonPath + ".private").value(item.private_()))
                    .andExpect(jsonPath(jsonPath + ".html_url").value(item.htmlUrl()))
                    .andExpect(jsonPath(jsonPath + ".git_url").value(item.gitUrl()))
                    .andExpect(jsonPath(jsonPath + ".language").value(item.language()))
                    .andExpect(jsonPath(jsonPath + ".created_at").value(item.createdAt()))
                    .andExpect(jsonPath(jsonPath + ".stargazers_count").value(item.stargazersCount()));
        }
    }

}