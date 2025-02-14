package com.ch.restdocs.swagger.user;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ch.restdocs.swagger.config.RestDocsSupport;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;


class UserControllerTest extends RestDocsSupport {

  private final UserService userService = mock(UserService.class);

  @Override
  protected Object initController() {
    return new UserController(userService);
  }

  @Test
  @DisplayName("서비스 상태 체크 API")
  void healthCheck() throws Exception {

    //when & then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/health-check")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("healthCheck"
            , resource(
                ResourceSnippetParameters.builder()
                    .tag("Health check")
                    .description("Check if the service is up and running")
                    .build()
            )
        ));
  }

  @Test
  @DisplayName("특정 사용자 조회 API")
  void readUserId() throws Exception {
    //given
    final String requestUserId = "1";
    final String name = "이찬형";
    final String email = "cksgud410@gmail.com";
    final int age = 11;
    final User user = new User(requestUserId, name, email, age);
    given(userService.findById(requestUserId))
        .willReturn(user);

    //when & then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{userId}", requestUserId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(requestUserId))
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.email").value(email))
        .andExpect(jsonPath("$.age").value(age))
        .andDo(document("readUserId"
            , resource(
                ResourceSnippetParameters.builder()
                    .tag("User")
                    .description("사용자 ID를 받아서 해당 사용자 정보를 조회하는 API")
                    .pathParameters(parameterWithName("userId").description("사용자 ID"))
                    .responseFields(
                        fieldWithPath("id").description("사용자 ID"),
                        fieldWithPath("name").description("사용자 이름"),
                        fieldWithPath("email").description("사용자 이메일"),
                        fieldWithPath("age").description("사용자 나이")
                    )
                    .responseSchema(Schema.schema("ReadUserByIdResponse"))
                    .build()
            )
        ));
  }
}