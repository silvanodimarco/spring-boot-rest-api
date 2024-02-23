package com.silvanodimarco.springbootrestapi.persistance.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(
        mappedBy = "product",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.REMOVE
        }
    )
    private List<Review> reviews;
}
