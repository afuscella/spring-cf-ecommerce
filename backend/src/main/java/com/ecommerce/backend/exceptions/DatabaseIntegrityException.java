package com.ecommerce.backend.exceptions;

import com.ecommerce.backend.utils.BackendConstants;

public class DatabaseIntegrityException extends BackendException {

	public static final String INTEGRITY_VIOLATION = "exception.database.integrityViolation";

	public DatabaseIntegrityException() {
		super(BackendConstants.BUNDLE.getString(INTEGRITY_VIOLATION));
	}

}
