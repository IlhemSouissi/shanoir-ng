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

package org.shanoir.challengeScores.data.access.repository;

import org.shanoir.challengeScores.data.model.Metric;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring repository interface.
 *
 * Allows to use basic CRUD methods on the parameterized type without even implementing this interface.
 * See {@link CrudRepository}.
 *
 * @author jlouis
 */
public interface MetricRepository extends CrudRepository<Metric, Long> {

	Iterable<Metric> findByStudiesId(Long studyId);

}
