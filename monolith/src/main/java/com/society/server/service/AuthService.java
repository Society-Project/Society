package com.society.server.service;

import com.society.server.dto.payload.SigninDTO;
import com.society.server.dto.payload.SignupDTO;
import com.society.server.exception.InvalidCredentialsException;
import com.society.server.exception.RoleNotFoundException;
import com.society.server.exception.UserAlreadyExistsException;
import com.society.server.model.entity.RoleEntity;
import com.society.server.model.entity.user.UserEntity;
import com.society.server.model.entity.user.UserPersonalInfo;
import com.society.server.model.enums.RoleEnum;
import com.society.server.repository.RoleRepository;
import com.society.server.repository.UserRepository;
import com.society.server.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                       RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public String signInUser(SigninDTO signinDto) {
        if (!userRepository.existsByUsername(signinDto.getUsernameOrEmail()) &&
                !userRepository.existsByEmail(signinDto.getUsernameOrEmail())) {
            throw new InvalidCredentialsException("Incorrect username!", HttpStatus.UNAUTHORIZED);
        }
        Optional<UserEntity> byUsername = userRepository
                .findByUsernameOrEmail(signinDto.getUsernameOrEmail(), signinDto.getUsernameOrEmail());

        if (!passwordEncoder.matches(signinDto.getPassword(), byUsername.get().getPassword())) {
            throw new InvalidCredentialsException("Incorrect password!", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinDto.getUsernameOrEmail(), signinDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateJwtToken(authentication);
    }

    @Transactional
    public void signUpUser(SignupDTO signupDto) {
        //check if username already exist
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            throw new UserAlreadyExistsException(HttpStatus.BAD_REQUEST, "User with such username already exists!");
        }

        //check if email already exists
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new UserAlreadyExistsException(HttpStatus.BAD_REQUEST, "User with such email already exists!");
        }

        RoleEntity role = roleRepository.findByRoleName(RoleEnum.USER).orElseThrow(()
                -> new RoleNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "No Default User Role in the database"));

        //create new user
        UserEntity user = modelMapper.map(signupDto, UserEntity.class);
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        userPersonalInfo.setBirthday(signupDto.getBirthday());
        user.setUserPersonalInfo(userPersonalInfo);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setRoles(Set.of(role));
        user.setEnabled(true);
        user.setLocked(false);
        user.setAccountExpired(false);
        user.setCredentialsExpired(false);

        userRepository.save(user);
    }
}
