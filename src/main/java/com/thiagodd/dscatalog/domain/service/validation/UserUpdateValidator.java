package com.thiagodd.dscatalog.domain.service.validation;

import com.thiagodd.dscatalog.api.exceptions.FieldMessage;
import com.thiagodd.dscatalog.domain.model.User;
import com.thiagodd.dscatalog.domain.model.dto.UserUpdateDto;
import com.thiagodd.dscatalog.domain.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    private final HttpServletRequest request;
    private final UserRepository userRepository;

    public UserUpdateValidator(HttpServletRequest httpServletRequest, UserRepository userRepository) {
        this.request = httpServletRequest;
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());

        if(user != null && userId != user.getId()){
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
