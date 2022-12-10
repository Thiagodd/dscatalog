package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Role;
import com.thiagodd.dscatalog.domain.model.User;
import com.thiagodd.dscatalog.domain.model.dto.RoleDto;
import com.thiagodd.dscatalog.domain.model.dto.UserDto;
import com.thiagodd.dscatalog.domain.repository.RoleRepository;
import com.thiagodd.dscatalog.domain.repository.UserRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    String MSG_ENTITY_NOT_FOUND = "[CUSTOM] Produto com id %d não foi encontrada!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] Produto com id %d não pode ser deletada, pois está em uso!";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserDto::new);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
        return new UserDto(user);
    }

    @Transactional
    public UserDto insert(UserDto userDto) {
        User user = new User();
        copyDtoToEntity(userDto, user);
        user = userRepository.save(user);
        return new UserDto(user);
    }

    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        try {
            User user = userRepository.getReferenceById(id);
            copyDtoToEntity(userDto, user);
            user = userRepository.save(user);
            return new UserDto(user);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseIntegratyViolationException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }

    public void copyDtoToEntity(UserDto dto, User user){
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        for (RoleDto catDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(catDto.getId());
            user.getRoles().add(role);
        }
    }
}
