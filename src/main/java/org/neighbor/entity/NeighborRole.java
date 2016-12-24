package org.neighbor.entity;

import javax.persistence.*;

@Entity
public class NeighborRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private RoleEnum userRole;
    @ManyToOne(targetEntity = NeighborUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private NeighborUser user;

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

    public void setUser(NeighborUser user) {
        this.user = user;
        this.userId = user.getId();
    }

    public NeighborUser getUser() {
        return user;
    }
}
