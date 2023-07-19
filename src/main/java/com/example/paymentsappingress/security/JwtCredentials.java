package com.example.paymentsappingress.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.context.annotation.Configuration;

@Data
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Configuration
public class JwtCredentials {

    private String authority;
    private String username;

}
