package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
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
            return ResponseGenerator.generateOrgAlreadyExistError();
        }
        NeighborOrg org = new NeighborOrg();
        org.setExtId(extId);
        org.setName(request.getName());
        org.setActive(true);

        NeighborOrg savedOrg = orgRepository.save(org);
        accountService.createDefaultAccountForOrgId(savedOrg.getId());
        GeneralResponse response = ResponseGenerator.CREATED;
        return response;
    }

    @Override
    public GeneralResponse update(UpdateOrgRequest updateOrgRequest) {
        Optional<NeighborOrg> byExtId = orgRepository.findByExtId(updateOrgRequest.getExtId());
        if (!byExtId.isPresent()) {
            return ResponseGenerator.generateOrgNotExistError();
        }
        NeighborOrg org = byExtId.get();
        org.setActive(updateOrgRequest.getActive());
        org.setName(updateOrgRequest.getName());
        orgRepository.save(org);
        return ResponseGenerator.OK;
    }

    @Override
    public GeneralResponse delete(DeleteOrgRequest deleteOrgRequest) {
        Optional<NeighborOrg> foundedOrg = orgRepository.findByExtId(deleteOrgRequest.getExtId());
        if (!foundedOrg.isPresent()) {
            return ResponseGenerator.generateOrgNotExistError();
        }
        NeighborOrg org = foundedOrg.get();
        List<NeighborAccount> accounts = accountService.findByOrg(org);
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
        Optional<NeighborOrg> byExtId = orgRepository.findByExtId(extId);
        if (byExtId.isPresent()) return orgMapper.orgToOrgDto(byExtId.get());
        else return null;//todo ???
    }

}
