package org.neighbor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NeighborAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orgId;
    private NeighborOrg org;
    private String accountNumber;
    private String accountUrn;
    private String ownerPhone;
    private String addressStreet;
    private String addressBuilding;
    private String addressFloor;
    private String addressFlat;
    private Date createdOn;
    private Date updatedOn;
    private Boolean isActive = true;

}
