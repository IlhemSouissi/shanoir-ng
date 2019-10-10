/**
 * Shanoir NG - Import, manage and share neuroimaging data
 * Copyright (C) 2009-2019 Inria - https://www.inria.fr/
 * Contact us on https://project.inria.fr/shanoir/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/gpl-3.0.html
 */

/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.shanoir.ng.solr.controler;

import org.shanoir.ng.shared.exception.ErrorModel;
import org.shanoir.ng.shared.exception.RestServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yyao
 *
 */
@Api(value = "solr", description = "the Solr API")
@RequestMapping("/solr")
public interface SolrApi {
	@ApiOperation(value = "", notes = "Index all data to Solr", response = Void.class, tags={})
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "indexed data", response = Void.class),
        @ApiResponse(code = 401, message = "unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "forbidden", response = Void.class),
        @ApiResponse(code = 422, message = "bad parameters", response = ErrorModel.class),
        @ApiResponse(code = 500, message = "unexpected error", response = ErrorModel.class) })
    @RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> indexAllToSolr() throws RestServiceException;

}
