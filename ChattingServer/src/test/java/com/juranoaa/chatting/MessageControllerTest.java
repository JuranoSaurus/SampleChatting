package com.juranoaa.chatting;

import com.juranoaa.chatting.rest.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/servlet-context.xml"})
public class MessageControllerTest {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void chatting_echo_test_post_method() throws Exception {
        Message message = new Message("Tae", "Hello my name is tae");

        byte[] requestBytes = TestUtil.convertObjectToJsonBytes(message.getMap());

        mockMvc.perform(post("/send")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(requestBytes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tae"))
                .andExpect(jsonPath("$.data").value("Hello my name is tae"));
    }

    @Test
    public void chatting_receive_test_get_metho() throws Exception{
        mockMvc.perform(get("/receive"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("kim"))
                .andExpect(jsonPath("$.data").value("Hello my name is kim"));
    }
}
