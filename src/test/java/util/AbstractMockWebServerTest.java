package util;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public abstract class AbstractMockWebServerTest {

  protected MockWebServer mockBackEnd;

  protected void beforeEach() throws IOException {
    mockBackEnd = new MockWebServer();
    mockBackEnd.start();
  }

  protected void afterEach() throws IOException {
    mockBackEnd.shutdown();
  }

  protected RestClient gerRestClient() {
    var baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
    return RestClient.builder()
        .requestFactory(new HttpComponentsClientHttpRequestFactory())
        .baseUrl(baseUrl)
        .build();
  }

  protected void mockBackEndEnqueueJsonStringBody(int code, String body) {
    final MockResponse response = new MockResponse();
    response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    response.setResponseCode(code);
    response.setBody(body);
    mockBackEnd.enqueue(response);
  }

  protected void mockBackEndEnqueueJsonNoBody(int code) {
    final MockResponse response = new MockResponse();
    response.setResponseCode(code);
    mockBackEnd.enqueue(response);
  }
}
