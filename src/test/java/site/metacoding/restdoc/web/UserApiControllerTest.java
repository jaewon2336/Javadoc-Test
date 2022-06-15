package site.metacoding.restdoc.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.restdoc.AbstractControllerTest;
import site.metacoding.restdoc.domain.User;
import site.metacoding.restdoc.util.keystore.MediaTypeImpl;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
// @AutoConfigureMockMvc // IoC 컨테이너에 MockMvc 등록
@SpringBootTest
public class UserApiControllerTest extends AbstractControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    @Test
    public void save_테스트() throws Exception {
        // given
        String content = new ObjectMapper().writeValueAsString(
                User.builder().username("ssar").password("1234").email("ssar@nate.com").build()); // json
                                                                                                  // 데이터

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/user")
                        .content(content)
                        .contentType(MediaTypeImpl.APPLICATION_JSON_UTF8)); // MediaType.APPLICATION_JSON_UTF8
                                                                            // @Deprecated 되어서
                                                                            // Impl로 만들어준 것

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ssar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ssar@nate.com"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }

    @Test
    public void find_한건_테스트() throws Exception {
        // given
        Integer id = 1;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/user/" + id));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ssar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ssar@nate.com"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }

    @Test
    public void find_전체_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/user"));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].username").value("ssar"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].email").value("ssar@nate.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].username").value("cos"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].email").value("cos@nate.com"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }
}
