package com.thiagodd.dscatalog.api.controller;

import com.thiagodd.dscatalog.domain.model.dto.RoleDto;
import com.thiagodd.dscatalog.domain.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<RoleDto>> findAll(Pageable pageable) {
        Page<RoleDto> roleList = roleService.findAll(pageable);
        return ResponseEntity.ok().body(roleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {
        RoleDto roleDto = roleService.findById(id);
        return ResponseEntity.ok().body(roleDto);
    }

    @PostMapping
    public ResponseEntity<RoleDto> insert(@RequestBody RoleDto roleDto){
        roleDto = roleService.insert(roleDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(roleDto.getId()).toUri();
        return ResponseEntity.created(uri).body(roleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody RoleDto roleDto){
        roleDto = roleService.update(id, roleDto);
        return ResponseEntity.ok().body(roleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
