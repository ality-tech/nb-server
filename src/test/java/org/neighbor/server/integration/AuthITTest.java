package org.neighbor.server.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.server.MainApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 *
 * Created by Konstantin Konyshev on 13/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MainApp.class)
@Transactional
public class AuthITTest {

    @Value("${server.schema}")
    private String schema;

    @Value("${server.host}")
    private String host;

    @Value("${server.contextPath}")
    private String contextPath;

    @Value("${server.port}")
    private Integer port;

    private String authEndpoint;

    private RestTemplate restTemplate;

    @Before
    public void init() {
        this.authEndpoint = "/auth/check";
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void testAuth_forbidden() throws IOException {
        try {
            restTemplate.exchange(buildUrl(authEndpoint), HttpMethod.POST, null, JsonError.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
            /*JsonError jsonError = new ObjectMapper().readValue(e.getResponseBodyAsString(), JsonError.class);
            assertThat(jsonError).isNotNull();
            assertThat(jsonError.getCode()).isEqualTo(ErrorCode.UNAUTHORIZED);*/
        }
    }

    @Test
    public void testAuth_ok() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        MediaType jsonUtf8 = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(jsonUtf8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");

        HttpEntity<GeneralResponse> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> response = restTemplate.exchange(buildUrl(authEndpoint), HttpMethod.POST, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String buildUrl(String endpoint) {
        return getContextURI() + endpoint;
    }

    private String getContextURI() {
        return schema + "://" + host + ":" + port + contextPath;
    }
}
