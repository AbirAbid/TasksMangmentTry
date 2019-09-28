package com.tasksmangment.pfa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tasksmangment.pfa.models.User;
import com.tasksmangment.pfa.repositories.RoleRepository;
import com.tasksmangment.pfa.repositories.UserRepository;
import com.tasksmangment.pfa.security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import com.tasksmangment.pfa.message.request.LoginForm;
import com.tasksmangment.pfa.message.request.SignUpForm;
import com.tasksmangment.pfa.message.response.JwtResponse;
import com.tasksmangment.pfa.message.response.ResponseMessage;
import com.tasksmangment.pfa.models.Role;
import com.tasksmangment.pfa.models.RoleName;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class UserRestController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Optional<User> getUserById(@PathVariable("id") Long id) {
		try {
			return userRepository.findById(id);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MANAGER')")
	public List<User> listUser() {
		try {
			return userRepository.findAll();
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)

	public User addUser(@RequestBody User user) {
		try {
			return userRepository.save(user);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)

	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		try {
			user.setId(id);
			return userRepository.saveAndFlush(user);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('MANAGER')")
	public void deleteUser(@PathVariable Long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());

		}
	}

	@PostMapping("/api/auth/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtProvider.generateJwtToken(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@PostMapping("/api/auth/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		try {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Probléme -> Ce nom d'utilisateur existe déjà"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<String>("Probléme -> Ce mail d'utilisateur existe déjà", HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		String role = (String) signUpRequest.getRole();
		// Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		// strRoles.forEach(role -> {
		switch (role) {
		case "manager":
			Role manager = roleRepository.findByName(RoleName.ROLE_MANAGER)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: manager Role not find."));
			roles.add(manager);

			break;
		case "ingenieur":
			Role ingenieur = roleRepository.findByName(RoleName.ROLE_INGENIEUR)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: ingenieur Role not find."));
			roles.add(ingenieur);

			break;
		default:
			Role technicienRole = roleRepository.findByName(RoleName.ROLE_TECHNICIEN)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: technicien Role not find."));
			roles.add(technicienRole);
		}
		// });
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
		}

	@GetMapping("/api/test/ingenieur")
	@PreAuthorize("hasRole('INGENIEUR')")
	public String userAccess() {
		return ">>> ingenieur Contents!";
	}

	@GetMapping("/api/test/technicien")
	@PreAuthorize("hasRole('TECHNICIEN')")
	public String projectManagementAccess() {
		return ">>> technicien Contents";
	}

	@GetMapping("/api/test/manager")
	@PreAuthorize("hasRole('MANAGER')")
	public String adminAccess() {
		return ">>> manager Contents";
	}
}
