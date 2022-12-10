package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Role;
import com.thiagodd.dscatalog.domain.model.dto.RoleDto;
import com.thiagodd.dscatalog.domain.repository.RoleRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    String MSG_ENTITY_NOT_FOUND = "[CUSTOM] Categoria com id %d não foi encontrada!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] Categoria com id %d não pode ser deletada, pois está em uso!";

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<RoleDto> findAll(Pageable pageable) {
        Page<Role> roleList = roleRepository.findAll(pageable);
        return roleList.map(RoleDto::new);
    }

    @Transactional(readOnly = true)
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
        return new RoleDto(role);
    }

    @Transactional
    public RoleDto insert(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role, "id");
        role = roleRepository.save(role);
        return new RoleDto(role);
    }

    @Transactional
    public RoleDto update(Long id, RoleDto RoleDto) {
        try {
            Role role = roleRepository.getReferenceById(id);
            BeanUtils.copyProperties(RoleDto, role, "id");
            role = roleRepository.save(role);
            return new RoleDto(role);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }
    }

    public void deleteById(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseIntegratyViolationException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
