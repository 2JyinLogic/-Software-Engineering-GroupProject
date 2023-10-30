//package edu.cpt202.group9.projb.home.integrationtest;
//
//import edu.cpt202.group9.projb.home.HomeController;
//import edu.cpt202.group9.projb.security.Account;
//import edu.cpt202.group9.projb.security.AccountService;
//import edu.cpt202.group9.projb.user.UserServices;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//
//public class HomeControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private AccountService accountService;
//
//    @Mock
//    private UserServices userServices;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        HomeController homeController = new HomeController();
//        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
//    }
//
//    @Test
//    public void testSignUp() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("userEmail", "test@example.com");
//        params.add("account.username", "test");
//        params.add("account.userPassword", "password");
//        params.add("account.userRole", "USER");
//
//        when(accountService.tryAddNewAccount(eq("test"), eq("password"))).thenReturn(true);
//
//        Account newAccount = new Account();
//        newAccount.setUsername("test");
//        newAccount.setUserPassword("password");
//        newAccount.setUserRole("USER");
//        when(accountService.findAccountByUsername(eq("test"))).thenReturn(Optional.of(newAccount));
//
//        mockMvc.perform(post("/sign-up").params(params))
//                .andExpect(status().isOk())
//                .andExpect(view().name("account/Login"));
//
//        verify(accountService, times(1)).tryAddNewAccount(eq("test"), eq("password"));
//        verify(accountService, times(1)).findAccountByUsername(eq("test"));
//        verify(userServices, times(1)).addUser(eq("test@example.com"), eq(newAccount));
//    }
//}
