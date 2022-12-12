package com.thiagodd.dscatalog.domain.service.validation;

import com.thiagodd.dscatalog.api.exceptions.FieldMessage;
import com.thiagodd.dscatalog.domain.model.User;
import com.thiagodd.dscatalog.domain.model.dto.UserInsertDto;
import com.thiagodd.dscatalog.domain.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDto> {

    private final UserRepository userRepository;

    public UserInsertValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDto dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());
        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
        if(user != null){
            list.add(new FieldMessage("email", "Já existe um usuário com esse email cadastrado."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
