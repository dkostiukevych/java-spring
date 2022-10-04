package com.qa.domain.products.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.qa.domain.products.views.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@SuppressWarnings("JavadocType")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;
    
    @NotNull
    @JsonView(Views.IdName.class)
    @Size(min = 1, max = 30)
    private String name;
    
    @NotNull
    @JsonView(Views.FullMessage.class)
    @Size(min = 1, max = 199)
    private String description;
    
    @NotNull
    @Max(10000)
    @JsonView(Views.FullMessage.class)
    private double price;

    @NotNull
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @JsonView(Views.FullMessage.class)
    private List<String> imageUrls;
    
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationDate;
    
}
