package com.yhj.erp.server.api.service;

import com.yhj.erp.common.dto.PageRequest;
import com.yhj.erp.common.dto.PageResponse;
import com.yhj.erp.server.api.dto.*;

/**
 * Server service interface.
 */
public interface ServerService {

    /**
     * Create a new server.
     *
     * @param request create request
     * @return created server
     */
    ServerDto create(ServerCreateRequest request);

    /**
     * Update a server.
     *
     * @param id      server ID
     * @param request update request
     * @return updated server
     */
    ServerDto update(String id, ServerUpdateRequest request);

    /**
     * Delete a server (soft delete).
     *
     * @param id server ID
     */
    void delete(String id);

    /**
     * Get a server by ID.
     *
     * @param id server ID
     * @return server
     */
    ServerDto getById(String id);

    /**
     * Get a server by asset code.
     *
     * @param assetCode asset code
     * @return server
     */
    ServerDto getByAssetCode(String assetCode);

    /**
     * List servers with pagination.
     *
     * @param pageRequest pagination
     * @param query       query filter
     * @return paginated servers
     */
    PageResponse<ServerDto> list(PageRequest pageRequest, ServerQueryRequest query);

    /**
     * Deploy a server to a cabinet.
     *
     * @param id      server ID
     * @param request deploy request
     * @return updated server
     */
    ServerDto deploy(String id, DeployRequest request);

    /**
     * Undeploy a server from cabinet.
     *
     * @param id     server ID
     * @param reason reason for undeploy
     * @return updated server
     */
    ServerDto undeploy(String id, String reason);

    /**
     * Scrap a server.
     *
     * @param id      server ID
     * @param request scrap request
     */
    void scrap(String id, ScrapRequest request);
}