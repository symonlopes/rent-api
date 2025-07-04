package br.com.symon.rentapi;

import br.com.symon.rentapi.model.UserRegistrationRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class UserRegistrationTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private TestUtils utils;

	private UserRegistrationRequest createValidUserRegistrationRequest() {
		return UserRegistrationRequest.builder()
				.name("Joe Doe")
				.email("joedoe@email.com")
				.password("valid@password99")
				.passwordConfirmation("valid@password99")
				.build();
	}

	@Test
	public void shouldCreateUserSuccessfully() throws Exception {

		var validEntity = createValidUserRegistrationRequest();

		mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(validEntity)))
				.andExpect(status().isCreated())
				.andReturn();


	}

}
