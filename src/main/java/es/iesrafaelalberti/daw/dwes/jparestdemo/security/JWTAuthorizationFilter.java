package es.iesrafaelalberti.daw.dwes.jparestdemo.security;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.User;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private UserRepository userRepository;

    public JWTAuthorizationFilter(ApplicationContext applicationContext) {
        this.userRepository = applicationContext.getBean(UserRepository.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String encabezado = httpServletRequest.getHeader("Authorization");
//        if(encabezado.equals("OK"))
//            setUpSpringAuthentication();
//        else
//            SecurityContextHolder.clearContext();

        // TODO: No keys hardcoded!!!
        if(httpServletRequest.getHeader("Authorization")!=null) {
            if (!httpServletRequest.getHeader("Authorization").startsWith("Bearer ")) {
                SecurityContextHolder.clearContext();
            } else {
                String jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
                try {
                    Claims claims = Jwts.parser().setSigningKey("pestillo".getBytes()).parseClaimsJws(jwtToken).getBody();
                    String username = claims.getSubject();
                    User user = userRepository.findUserByUsername(username)
                            .orElseThrow(() -> new EntityNotFoundException());
                    if(!user.getToken().equals(jwtToken))
                        throw new Exception();
                    Hibernate.initialize(user.getRoles());
                    setUpSpringAuthentication(user);
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

//    private void setUpSpringAuthentication(/*Claims claims*/) {
    private void setUpSpringAuthentication(User user) {
        //List<String> authorities = (List) claims.get("authorities");
//List<String> authoritiesText = new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_GOD"));
//List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//for( String text: authoritiesText ) {
//    authorities.add(new SimpleGrantedAuthority(text));
//}
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin", null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin", null,
//                                                        authorities);
//List<String> authorities = (List) claims.get("authorities");
//        List<String> authoritiesText = new ArrayList<>(Arrays.asList("ROLE_LOSER", "ROLE_GAMER"));
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for( String text: authoritiesText ) {
//            authorities.add(new SimpleGrantedAuthority(text));
//        }

//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin", null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("lalalala", null,
//                                                        authorities);
        Hibernate.initialize(user.getRoles());
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null,
                            user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
