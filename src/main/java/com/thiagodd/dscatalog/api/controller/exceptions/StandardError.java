package com.thiagodd.dscatalog.api.controller.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = -8170302065552164213L;
    private Instant timestamp;
    private Integer status;
    private String error;
    private String messege;
    private String path;



}
