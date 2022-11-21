package com.udacity.pricing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PricingServiceUnitTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetPrices() throws Exception {
		mockMvc.perform(get("/services/prices"))
			.andExpect(status().isOk());
	}
}
