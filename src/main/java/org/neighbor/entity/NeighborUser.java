package org.neighbor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NeighborUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long accountId;
    private NeighborAccount account;
    private String userUrn;
    private String login;
    private String pinCode;
    private String userPhone;
    private ActivationStatus activationStatus;
    private Date createdOn;
    private Date updatedOn;

}
