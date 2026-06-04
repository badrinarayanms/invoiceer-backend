//package com.badri.invoice.security;
//
//import com.badri.invoice.model.User;
//import com.badri.invoice.repository.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseCookie;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//
//    @Value("${FRONTEND_URL}")
//    private String frontendUrl;
//
//    public OAuth2LoginSuccessHandler(
//            JwtUtil jwtUtil,
//            UserRepository userRepository
//    ) {
//        this.jwtUtil = jwtUtil;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    ) throws IOException {
//
//        System.out.println("🔥🔥🔥 SUCCESS HANDLER CALLED! 🔥🔥🔥");
//        System.out.println("Request URI: " + request.getRequestURI());
//        System.out.println("Authentication: " + authentication);
//
//        // Get user info from Google
//        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
//        String email = oidcUser.getEmail();
//        String name = oidcUser.getFullName();
//
//        System.out.println("🔥 OAuth2 Success! Email: " + email); // Debug log
//
//        // Find or create user in database
//        User user = userRepository
//                .findByEmail(email)
//                .orElseGet(() -> {
//                    User newUser = new User();
//                    newUser.setEmail(email);
//                    newUser.setProvider("GOOGLE");
//                    newUser.setCompanyName(name); // You can use Google name as initial company name
//                    return userRepository.save(newUser);
//                });
//
//        // Generate JWT token
//        String token = jwtUtil.generateToken(user.getEmail());
//
//        // Create HTTP-only cookie with JWT
//        ResponseCookie cookie = ResponseCookie.from("JWT", token)
//                .httpOnly(true)
//                .secure(false)  // ⚠️ Set to true in production (HTTPS)
//                .sameSite("Lax")
//                .path("/")
//                .maxAge(24 * 60 * 60) // 24 hours
//                .build();
//
//        response.addHeader("Set-Cookie", cookie.toString());
//
//        System.out.println("🔥 Redirecting to: " + frontendUrl); // Debug log
//
//        // Redirect to frontend
//        getRedirectStrategy().sendRedirect(request, response, frontendUrl);
//    }
//}