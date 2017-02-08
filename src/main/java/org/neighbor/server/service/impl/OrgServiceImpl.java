package org.neighbor.server.service.impl;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.*;
import org.neighbor.server.entity.NeighborAccountEntity;
import org.neighbor.server.entity.NeighborOrgEntity;
import org.neighbor.server.mappers.NeighborOrgToOrgDto;
import org.neighbor.server.repository.OrgRepository;
import org.neighbor.server.service.AccountService;
import org.neighbor.server.service.OrgService;
import org.neighbor.server.utils.ResponseGenerator;
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
        Optional<NeighborOrgEntity> byExtId = orgRepository.findByExtId(extId);
        if (byExtId.isPresent()) {
            return ResponseGenerator.generateOrgAlreadyExistError();
        }
        NeighborOrgEntity org = new NeighborOrgEntity();
        org.setExtId(extId);
        org.setName(request.getName());
        org.setActive(true);

        NeighborOrgEntity savedOrg = orgRepository.save(org);
        accountService.createDefaultAccountForOrgId(savedOrg);
        GeneralResponse response = ResponseGenerator.CREATED;
        return response;
    }

    @Override
    public GeneralResponse update(UpdateOrgRequest updateOrgRequest) {
        Optional<NeighborOrgEntity> byExtId = orgRepository.findByExtId(updateOrgRequest.getExtId());
        if (!byExtId.isPresent()) {
            return ResponseGenerator.generateOrgNotExistError();
        }
        NeighborOrgEntity org = byExtId.get();
        org.setActive(updateOrgRequest.getActive());
        org.setName(updateOrgRequest.getName());
        orgRepository.save(org);
        return ResponseGenerator.OK;
    }

    @Override
    public GeneralResponse delete(DeleteOrgRequest deleteOrgRequest) {
        Optional<NeighborOrgEntity> foundedOrg = orgRepository.findByExtId(deleteOrgRequest.getExtId());
        if (!foundedOrg.isPresent()) {
            return ResponseGenerator.generateOrgNotExistError();
        }
        NeighborOrgEntity org = foundedOrg.get();
        List<NeighborAccountEntity> accounts = accountService.findByOrg(org);
        if (accounts.isEmpty()) {
            orgRepository.delete(org);
            return ResponseGenerator.OK;
        } else if (accounts.size() == 1 && accounts.contains(accountService.findDefaultByOrgIdAndOwnerPhone(org.getId(), "0"))) {
            accountService.delete(accounts.get(0));
            orgRepository.delete(org);
            return ResponseGenerator.OK;
        }

        return ResponseGenerator.generateOrgHasLinkedEntitiesError();
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
        Optional<NeighborOrgEntity> byExtId = orgRepository.findByExtId(extId);
        if (byExtId.isPresent()) return orgMapper.orgToOrgDto(byExtId.get());
        else return null;//todo ???
    }

}
