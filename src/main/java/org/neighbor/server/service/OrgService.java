package org.neighbor.server.service;

import org.neighbor.api.*;
import org.neighbor.api.organizaton.*;

import java.util.List;

public interface OrgService {
    GeneralResponse create(CreateOrgRequest request);

    GeneralResponse update(UpdateOrgRequest updateOrgRequest);

    GeneralResponse delete(DeleteOrgRequest deleteOrgRequest);

    List<OrgDto> findAll();

    OrgDto getById(GetOrgByIdRequest id);

    OrgDto getById(String extId);

}
