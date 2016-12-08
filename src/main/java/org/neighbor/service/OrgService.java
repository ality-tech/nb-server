package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateOrgRequest;

public interface OrgService {
    GeneralResponse create(CreateOrgRequest request);
}
