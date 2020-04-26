package com.wedsite.sale.entity;



import lombok.*;

import javax.persistence.*;

/**
 * The type Role entity.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role")
    private String role;

}
