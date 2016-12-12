package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.repository.NeighborOrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private NeighborOrgRepository orgRepository;

    @Override
    //todo add response with status
    public GeneralResponse create(CreateOrgRequest request) {
        String extId = request.getExtId();
        NeighborOrg org = new NeighborOrg();
        org.setExtId(extId);
        org.setName(request.getName());
        org.setActive(true);
        orgRepository.save(org);
        GeneralResponse response = new GeneralResponse(201, null);
        return response;
    }
}
