package com.ibm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Request body for creating or updating student")

public class StudentDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // ← Integer not int
    
    @NotNull(message="name cannot be null")
    @NotBlank(message="message cannot be blank")
    @Size(min=2,max=30,message="please make sure the name size is between 2 and 30")
    @Schema(
            description = "Full name of student",
            example = "Srihari",
            requiredMode = Schema.RequiredMode.REQUIRED
        )
    private String name;
	
}
