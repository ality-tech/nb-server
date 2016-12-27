package org.neighbor.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NeighborUserStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long usrId;
    @ManyToOne(targetEntity = NeighborUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private NeighborUser user;
    @Enumerated(EnumType.STRING)
    private ActivationStatus activationStatus;
    private Date createdOn;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public NeighborUser getUser() {
        return user;
    }

    public void setUser(NeighborUser user) {
        this.user = user;
        this.usrId = user.getId();
    }

    public ActivationStatus getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatus activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
