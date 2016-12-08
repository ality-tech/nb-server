package org.neighbor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NeighborUserStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long usrId;
    private NeighborUser user;
    private ActivationStatus activationStatus;
    private Date createdOn;
    private String remark;
}
