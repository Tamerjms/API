package med.voll.API.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.API.domain.usuarios.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRespository usuarioRespository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //System.out.println("El filtro esta haciendo llamado");//esta fue una prueba para ver si esta siendo aplicado el filtro

        //Obtener el token del header
        var authHeader = request.getHeader("Authorization");//.replace("Bearer", "");
//        if (token == null || token == ""){
//            throw new RuntimeException("El token enviado no es valido");
//        }//este if nos esta bloqueando el generador de token ya que al utilizarlo siempre esta vacio
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
//            System.out.println(token);
//            System.out.println(tokenService.getSubject(token));//Este usuario tiene sesion?
            var nombreUsuario = tokenService.getSubject(token);//extract username
            if(nombreUsuario != null){
                //Token valido
                var usuario = usuarioRespository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());//forzamos el inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);//se setea de manera manual el inicio de sesion
            }
        }
        filterChain.doFilter(request, response);
    }
}
