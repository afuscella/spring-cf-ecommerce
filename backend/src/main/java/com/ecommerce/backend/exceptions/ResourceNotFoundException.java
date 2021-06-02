package com.ecommerce.backend.exceptions;

import com.ecommerce.backend.utils.BackendConstants;

public class ResourceNotFoundException extends BackendException {

	public static final String NOT_FOUND = "exception.entity.notFound";

	public ResourceNotFoundException() {
		super(BackendConstants.BUNDLE.getString(NOT_FOUND));
	}

}
