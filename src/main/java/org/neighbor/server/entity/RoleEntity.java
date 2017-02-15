package org.neighbor.server.entity;

import org.neighbor.api.RoleEnum;

import javax.persistence.*;

@Entity(name = "neighbor_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private RoleEnum userRole;
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(RoleEnum userRole) {
        this.userRole = userRole;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        this.userId = user.getId();
    }

    public UserEntity getUser() {
        return user;
    }
}
