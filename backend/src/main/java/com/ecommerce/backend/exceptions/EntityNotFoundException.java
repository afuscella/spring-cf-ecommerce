package com.ecommerce.backend.exceptions;

import com.ecommerce.backend.utils.BackendConstants;

public class EntityNotFoundException extends BackendException {

	public static final String NOT_FOUND = "exception.entity.notFound";

	public EntityNotFoundException() {
		super(BackendConstants.BUNDLE.getString(NOT_FOUND));
	}

}
