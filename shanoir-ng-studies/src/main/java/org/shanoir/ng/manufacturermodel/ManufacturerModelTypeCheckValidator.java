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

package org.shanoir.ng.manufacturermodel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates if a manufacturer model is correct depending on its type.
 * 
 * @author msimon
 *
 */
public class ManufacturerModelTypeCheckValidator
		implements ConstraintValidator<ManufacturerModelTypeCheck, ManufacturerModel> {

	@Override
	public void initialize(final ManufacturerModelTypeCheck constraintAnnotation) {
	}

	@Override
	public boolean isValid(final ManufacturerModel manufacturerModel, final ConstraintValidatorContext context) {
		switch (manufacturerModel.getDatasetModalityType()) {
		case MR_DATASET:
			// Magnetic field mandatory for MR models
			if (manufacturerModel.getMagneticField() == null) {
				context.disableDefaultConstraintViolation();
		        context
		            .buildConstraintViolationWithTemplate("MR manufacturer model has no magnetic field!")
		            .addConstraintViolation();
				return false;
			}
		default:
			// No magnetic field for other models
			manufacturerModel.setMagneticField(null);
			return true;
		}
	}

}
