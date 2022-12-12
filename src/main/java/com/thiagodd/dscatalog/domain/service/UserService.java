package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Role;
import com.thiagodd.dscatalog.domain.model.User;
import com.thiagodd.dscatalog.domain.model.dto.RoleDto;
import com.thiagodd.dscatalog.domain.model.dto.UserDto;
import com.thiagodd.dscatalog.domain.model.dto.UserInsertDto;
import com.thiagodd.dscatalog.domain.model.dto.UserUpdateDto;
import com.thiagodd.dscatalog.domain.repository.RoleRepository;
import com.thiagodd.dscatalog.domain.repository.UserRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    String MSG_ENTITY_NOT_FOUND_BY_ID = "[CUSTOM] Usuario com id %d não foi encontrado!";
    String MSG_ENTITY_NOT_FOUND_BY_EMAIL = "[CUSTOM] Usuario com email %s não foi encontrado!";
    String MSG_ENTITY_FOUND_BY_EMAIL = "[CUSTOM] Usuario com email %s foi encontrado!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] usuario com id %d não pode ser deletada, pois está em uso!";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserDto::new);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND_BY_ID, id)
        ));
        return new UserDto(user);
    }

    @Transactional
    public UserDto insert(UserInsertDto userDto) {
        User user = new User();
        copyDtoToEntity(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.save(user);
        return new UserDto(user);
    }

    @Transactional
    public UserDto update(Long id, UserUpdateDto userDto) {
        try {
            User user = userRepository.getById(id);
            copyDtoToEntity(userDto, user);
            user = userRepository.save(user);
            return new UserDto(user);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND_BY_ID, id));
        }
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND_BY_ID, id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseIntegratyViolationException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }

    public void copyDtoToEntity(UserDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        for (RoleDto catDto : dto.getRoles()) {
            Role role = roleRepository.getById(catDto.getId());
            user.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null){
            logger.error(String.format(MSG_ENTITY_NOT_FOUND_BY_EMAIL, username));
            throw new UsernameNotFoundException("Email not fount");
        }
        logger.info(String.format(MSG_ENTITY_FOUND_BY_EMAIL, username));
        return userRepository.findByEmail(username);
    }
}
