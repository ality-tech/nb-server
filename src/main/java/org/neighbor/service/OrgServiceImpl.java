package org.neighbor.service;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.repository.NeighborOrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private NeighborOrgRepository orgRepository;

    @Autowired
    private NeighborAccountService accountService;

    @Override
    //todo add response with status
    public GeneralResponse create(CreateOrgRequest request) {
        String extId = request.getExtId();
        NeighborOrg byExtId = orgRepository.findByExtId(extId);
        if (byExtId != null) {
            return generateAlreadyExistError();
        }
        NeighborOrg org = new NeighborOrg();
        org.setExtId(extId);
        org.setName(request.getName());
        org.setActive(true);
        NeighborOrg savedOrg = orgRepository.save(org);
        accountService.createDefaultAccountForOrgId(savedOrg.getId());
        GeneralResponse response = new GeneralResponse(201, null);
        return response;
    }

    private GeneralResponse generateAlreadyExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }
}
