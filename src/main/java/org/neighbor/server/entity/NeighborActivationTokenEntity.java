package org.neighbor.server.entity;

import org.neighbor.api.TokenStatus;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "neighbor_activation_token")
public class NeighborActivationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(targetEntity = NeighborUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private NeighborUserEntity user;
    @Column (name = "token")
    private String token;
    @Enumerated(EnumType.STRING)
    @Column (name = "token_status")
    private TokenStatus tokenStatus;
    @Column (name = "created_on")
    private Date createdOn;
    @Column (name = "valid_to")
    private Date validTo;


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

    public NeighborUserEntity getUser() {
        return user;
    }

    public void setUser(NeighborUserEntity user) {
        this.user = user;
        this.userId = user.getId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
