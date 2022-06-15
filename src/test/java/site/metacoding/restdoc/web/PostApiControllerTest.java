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
import site.metacoding.restdoc.domain.Post;
import site.metacoding.restdoc.util.keystore.MediaTypeImpl;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
// @AutoConfigureMockMvc // IoC 컨테이너에 MockMvc 등록
@SpringBootTest
public class PostApiControllerTest extends AbstractControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    @Test
    public void save_테스트() throws Exception {
        // given
        String content = new ObjectMapper().writeValueAsString(
                Post.builder().title("스프링부트").content("재밌어요").build()); // json
                                                                        // 데이터

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/post")
                        .content(content)
                        .contentType(MediaTypeImpl.APPLICATION_JSON_UTF8)); // MediaType.APPLICATION_JSON_UTF8
                                                                            // @Deprecated 되어서
                                                                            // Impl로 만들어준 것

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("스프링부트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("재밌어요"))
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
                        .get("/api/post/" + id));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("스프링부트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("재밌어요"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }

    @Test
    public void find_전체_테스트() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/post"));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].title").value("스프링부트"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].content").value("재밌어요"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].title").value("파이썬"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].content").value("재미없어요"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document);
    }
}
