package br.com.symon.rentapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
@Component
public class TestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T  parseResponse(MvcResult result, Class<T> errorResponseClass) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), errorResponseClass);
    }

    public boolean hasErrorOnField(String field, ErrorResponse errorResponse) {
        return errorResponse.getErrors().stream().anyMatch(error -> field.equals(error.field()));
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
