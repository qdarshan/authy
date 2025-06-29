package org.arsh.authy.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tenants")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "tenantId", nullable = false, unique = true)
    private String tenantId;

    @Column(nullable = false)
    private String name;

//    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserEntity> users;

}
