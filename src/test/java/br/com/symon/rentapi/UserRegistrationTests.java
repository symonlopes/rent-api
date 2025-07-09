package br.com.symon.rentapi;

import br.com.symon.rentapi.model.UserRegistrationRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
				.password("vAlid@password99")
				.passwordConfirmation("vAlid@password99")
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


	@Test
	public void shouldNotCreateUserWithDifferentPasswords() throws Exception {

		var entity = createValidUserRegistrationRequest();
		entity.setPasswordConfirmation("different");

		mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(entity)))
				.andExpect(status().isBadRequest())
				.andReturn();


	}

	@Test
	public void shouldNotCreateUserWithTooShortPassword() throws Exception {

		var entity = createValidUserRegistrationRequest();

		entity.setPassword("@Ma1");
		entity.setPasswordConfirmation("@Ma1");

		MvcResult result = mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(entity)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("ErrorResponse: {}", errorResponse);

	}

	@Test
	public void shouldNotCreateUserWithPasswordThtContainsSpaces() throws Exception {

		var entity = createValidUserRegistrationRequest();

		entity.setPassword("vAlid@ password99");
		entity.setPasswordConfirmation("vAlid@ password99");

		MvcResult result = mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(entity)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("ErrorResponse: {}", errorResponse);

	}


	@Test
	public void shouldNotCreateUserWithTooBigPassword() throws Exception {

		var entity = createValidUserRegistrationRequest();

		entity.setPassword("vAlid@password99vAlid@password99vAlid@password99vAlid@password99vAlid@password99");
		entity.setPasswordConfirmation("vAlid@password99vAlid@password99vAlid@password99vAlid@password99vAlid@password99");

		MvcResult result = mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(utils.getObjectMapper().writeValueAsString(entity)))
				.andExpect(status().isBadRequest())
				.andReturn();

		var errorResponse = utils.parseResponse(result, ErrorResponse.class);
		log.info("ErrorResponse: {}", errorResponse);

	}

}
