package med.voll.API.controller;

import jakarta.validation.Valid;
import med.voll.API.domain.usuarios.DatosUtenticacionUsuario;
import med.voll.API.domain.usuarios.Usuario;
import med.voll.API.infra.security.DatosJWTToken;
import med.voll.API.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosUtenticacionUsuario datosUtenticacionUsuario){

        Authentication authToken = new UsernamePasswordAuthenticationToken(datosUtenticacionUsuario.login(),
                datosUtenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());//El principal se utiliza para devolver el objeto que ya fue autenticado
        //return ResponseEntity.ok(JWTToken);//con este lo que hacemos es que devolvemos el token en insomnia
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));//vamos a devolver un DTO en vez del token
    }
}
