package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.*;

import java.util.List;

public interface OrgService {
    GeneralResponse create(CreateOrgRequest request);

    GeneralResponse update(UpdateOrgRequest updateOrgRequest);

    GeneralResponse delete(DeleteOrgRequest deleteOrgRequest);

    List<OrgDto> findAll();

    OrgDto getById(GetOrgByIdRequest id);

}
