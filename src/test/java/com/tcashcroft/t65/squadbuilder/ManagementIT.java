package com.tcashcroft.t65.squadbuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

/** Integration tests for the management/actuator endpoints. */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ManagementIT {

  /**
   * Injection point for the randomly-selected port number for the Spring Boot management services.
   *
   * @see LocalServerPort
   */
  @LocalManagementPort private int managementPort;

  /** Verifies the health endpoint is working. */
  @Test
  public void testHealth() {
    RestTemplate rest = new RestTemplate();
    String uri = "http://localhost:" + managementPort + "/health";

    // Wrap in try-catch if needing to assert 4xx or 5xx responses.
    ResponseEntity<JsonNode> response = rest.getForEntity(uri, JsonNode.class);

    // Verify that the correct response code was sent.
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Verify that the correct response body was sent.
    JsonNode body = response.getBody();
    assertEquals("UP", body.path("status").asText());
  }
}
