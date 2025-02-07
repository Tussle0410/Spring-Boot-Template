package com.module.example.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.module.example.config.RestDocsSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;


class CheckControllerTest extends RestDocsSupport {
  @Override
  protected Object initController() {
    return new CheckController();
  }
  @Test
  void healthCheck() throws Exception {
    mockMvc.perform(RestDocumentationRequestBuilders.get("/health-check")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("t"
            , resource(
                ResourceSnippetParameters.builder()
                    .tag("Health check")
                    .description("Check if the service is up and running")
                    .build()
            )
        ));
  }

  @Test
  void loggingCheck() {
  }


}