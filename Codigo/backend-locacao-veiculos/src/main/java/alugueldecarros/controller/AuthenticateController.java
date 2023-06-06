package alugueldecarros.controller;

import alugueldecarros.models.RequestEntity.LoginRequest;
import alugueldecarros.models.ResponseEntity.LoginResponse;
import alugueldecarros.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticateController {

	@Autowired
	private AuthService authService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> createAuthenticationToken(
			@RequestBody LoginRequest authenticationRequest) throws Exception {

		return ResponseEntity.ok(this.authService.login(authenticationRequest));

	}
}
