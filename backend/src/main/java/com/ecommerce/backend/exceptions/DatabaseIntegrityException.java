package com.ecommerce.backend.exceptions;

import com.ecommerce.backend.utils.BackendConstants;

public class DatabaseException extends BackendException {

	public static final String NOT_FOUND = "exception.entity.notFound";

	public DatabaseException() {
		super(BackendConstants.BUNDLE.getString(NOT_FOUND));
	}

}
