package com.cristianml.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "category")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Can't be empty.")
    private String name;

    // This slug is for validating duplicated registers in DB.
    private String slug;

}
