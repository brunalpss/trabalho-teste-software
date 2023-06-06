package alugueldecarros.service;

import alugueldecarros.models.RequestEntity.LoginRequest;
import alugueldecarros.models.ResponseEntity.LoginResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    LoginResponse login(LoginRequest authenticationRequest) throws Exception;

    Authentication getAuthenticatedUser();
}
