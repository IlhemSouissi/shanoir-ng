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

package org.shanoir.ng.datasetacquisition;

import java.util.List;

import org.shanoir.ng.shared.dto.IdNameDTO;

/**
 * Simple dataset acquisition DTO with information for examination.
 * 
 * @author msimon
 *
 */
public class ExaminationDatasetAcquisitionDTO extends IdNameDTO {

	private List<IdNameDTO> datasets;

	/**
	 * @return the datasets
	 */
	public List<IdNameDTO> getDatasets() {
		return datasets;
	}

	/**
	 * @param datasets
	 *            the datasets to set
	 */
	public void setDatasets(List<IdNameDTO> datasets) {
		this.datasets = datasets;
	}

}
