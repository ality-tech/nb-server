package org.neighbor.service;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.*;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.mappers.NeighborOrgToOrgDto;
import org.neighbor.repository.OrgRepository;
import org.neighbor.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private NeighborOrgToOrgDto orgMapper;

    @Override
    //todo add response with status switch to standart
    public GeneralResponse create(CreateOrgRequest request) {
        String extId = request.getExtId();
        Optional<NeighborOrg> byExtId = orgRepository.findByExtId(extId);
        if (byExtId.isPresent()) {
            return generateAlreadyExistError();
        }
        NeighborOrg org = new NeighborOrg();
        org.setExtId(extId);
        org.setName(request.getName());
        org.setActive(true);

        NeighborOrg savedOrg = orgRepository.save(org);
        accountService.createDefaultAccountForOrgId(savedOrg.getId());
        GeneralResponse response = new GeneralResponse(201, null);//201 according to specs
        return response;
    }

    @Override
    public GeneralResponse update(UpdateOrgRequest updateOrgRequest) {
        Optional<NeighborOrg> byExtId = orgRepository.findByExtId(updateOrgRequest.getExtId());
        if (!byExtId.isPresent()) {
            return ResponseGenerator.ORG_NOTEXIST_ERROR;
        }
        NeighborOrg org = byExtId.get();
        org.setActive(updateOrgRequest.getActive());
        org.setName(updateOrgRequest.getName());
        orgRepository.save(org);
        return generateOkResponse();
    }

    @Override
    public GeneralResponse delete(DeleteOrgRequest deleteOrgRequest) {
        Optional<NeighborOrg> foundedOrg = orgRepository.findByExtId(deleteOrgRequest.getExtId());
        if (!foundedOrg.isPresent()) {
            return ResponseGenerator.ORG_NOTEXIST_ERROR;
        }
        NeighborOrg org = foundedOrg.get();
        List<NeighborAccount> accounts = accountService.findByOrg(org);
        if (accounts.isEmpty()) {
            orgRepository.delete(org);
            return generateOkResponse();
        } else if (accounts.size() == 1 && accounts.contains(accountService.findDefaultByOrgIdAndOwnerPhone(org.getId(), "0"))) {
            accountService.delete(accounts.get(0));
            orgRepository.delete(org);
            return generateOkResponse();
        }

        return generateHasLinkedEntitiesError();
    }

    @Override
    public List<OrgDto> findAll() {
        return orgMapper.iterableOrgsToListOrgDtos(orgRepository.findAll());
    }

    @Override
    public OrgDto getById(GetOrgByIdRequest id) {
        return getById(id.getId());
    }

    @Override
    public OrgDto getById(String extId) {
        Optional<NeighborOrg> byExtId = orgRepository.findByExtId(extId);
        if (byExtId.isPresent()) return orgMapper.orgToOrgDto(byExtId.get());
        else return null;//todo ???
    }

    private GeneralResponse generateOkResponse() {
        return new GeneralResponse(200, null);
    }

    private GeneralResponse generateAlreadyExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private GeneralResponse generateHasLinkedEntitiesError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_HAS_LINKED_ENTITIES);
        error.setMessage("There are exist non default accounts linked to given org");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }
}
