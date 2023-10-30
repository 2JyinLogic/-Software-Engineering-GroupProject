/**
 * AccessControlTest.java
 * 
 * Authored by Guanyuming He
 * Last modified 2023.4.24
 */
package edu.cpt202.group9.projb.security.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import edu.cpt202.group9.projb.config.MvcConfig;
import edu.cpt202.group9.projb.config.WebSecurityConfig;
import edu.cpt202.group9.projb.security.AccountUserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

/**
 * Tests if accessing specific page give expected results based on the security configuration.
 * 
 * If you are trying to write integration tests by studying mine, there are some notes: 
 * 1. Be sure to create mock beans for every bean your controller uses.
 * Otherwise, there will be errors.
 * 2. If you don't want security so you don't have to use @WithMockUser, then comment out the line that I marked.
 * 
 * @author Guanyuming He
 * @since 2023.4.17
 * @version 2023.4.24
 */
@ExtendWith(SpringExtension.class) /* Don't trust VSCode's warning. This line IS necessary. */
@ContextConfiguration(classes = {MvcConfig.class, WebSecurityConfig.class})
@WebMvcTest
public class AccessControlTest {
    
	@Autowired
	private WebApplicationContext context;

    /* For every bean your controller uses, you should add it as a @MockBean here. */
    @MockBean
    AccountUserDetailsService udService;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
            /* In your own tests, if you don't want the trouble of @WithMockUser, then disable security by commenting out the following line */
            .apply(SecurityMockMvcConfigurers.springSecurity())
			.build();
	}

    /**
     * Expected to get redirected when accessing restricted pages with no user.
     * @throws Exception
     */
    @Test
	void testRestrictedPagesWhenNotAuthenticated() throws Exception {
        /* HTTP 302: the resource requested has been temporarily moved to somewhere else */
        /* HTTP 401: unauthorized */
		mvc.perform(get("/mainpage"))
				    .andExpect(status().is(401));
        mvc.perform(get("/management_system"))
				    .andExpect(status().is(401));
	}

    /**
     * Expected to be forbidden
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"USER"})
    public void testRestrictedPageWrongRoleUser() throws Exception {
        /* HTTP 403: Forbidden */
        mvc.perform(get("/manager/managerorders"))
        .andExpect(status().is(403));
    }

        /**
     * Expected to be forbidden
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"MANAGER"})
    public void testRestrictedPageWrongRoleMgr() throws Exception {
        /* HTTP 403: Forbidden */
        mvc.perform(get("/history"))
        .andExpect(status().is(403));
    }

    /**
     * Expected to be OK
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"MANAGER"})
    public void testRestrictedPageCorrectRoleMgr() throws Exception {
        /* HTTP 200: OK */
        mvc.perform(get("/manager/statistical-reports"))
        .andExpect(status().is(200));
    }

    /**
     * Expected to be OK
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"USER"})
    public void testRestrictedPageCorrectRoleUser() throws Exception {
        /* HTTP 200: OK */
        mvc.perform(get("/appointment"))
        .andExpect(status().is(200));
    }
}
