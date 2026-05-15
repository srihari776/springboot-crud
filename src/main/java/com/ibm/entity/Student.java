package com.ibm.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor          // ← Lombok generates: Student() {}
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // ← Integer not int
    
    @NotNull(message="name cannot be null")
    @NotBlank(message="message cannot be blank")
    @Size(min=2,max=30,message="please make sure the name size is between 2 and 30")
    private String name;
}
// ✅ REMOVED @AllArgsConstructor  ← This was the conflict!