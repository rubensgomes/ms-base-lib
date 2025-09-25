/*
 * Copyright 2025 Rubens Gomes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Error handling and error code management for microservices applications.
 *
 * <p>This package provides a comprehensive framework for standardizing error handling across
 * microservices architectures. It defines structured error codes, error data models, and
 * standardized error reporting mechanisms that ensure consistent error communication between
 * services and to end users.
 *
 * <h2>Package Overview</h2>
 *
 * <p>The error handling framework is built on two core components that work together to provide a
 * complete error management solution:
 *
 * <ul>
 *   <li><strong>Error Code Contracts:</strong> Standardized interfaces for defining error
 *       identifiers with both machine-readable codes and human-readable descriptions
 *   <li><strong>Error Data Structures:</strong> Comprehensive error information containers that
 *       support both structured error reporting and diagnostic information
 * </ul>
 *
 * <h2>Core Components</h2>
 *
 * <h3>{@link com.rubensgomes.msbaselib.error.ApplicationErrorCode}</h3>
 *
 * <p>Defines the fundamental contract for all error codes in the system. This interface ensures
 * that every error code provides:
 *
 * <ul>
 *   <li><strong>Unique Identifier:</strong> A machine-readable code for programmatic handling
 *   <li><strong>Human Description:</strong> Clear, user-friendly error message
 *   <li><strong>Validation Constraints:</strong> Both fields are mandatory and must be non-blank
 * </ul>
 *
 * <h3>{@link com.rubensgomes.msbaselib.error.ApplicationError}</h3>
 *
 * <p>Defines the contract for error objects that encapsulate error codes, descriptions, and
 * optional native error details for diagnostics. This interface supports both end-user
 * communication and system-level troubleshooting.
 *
 * <h2>Integration Notes</h2>
 *
 * <ul>
 *   <li>Integrate with frameworks such as Spring for global exception handling and error response
 *       mapping.
 *   <li>Use validation annotations to enforce error data integrity.
 *   <li>Leverage error codes for internationalization and monitoring.
 *   <li>Follow best practices for microservices error propagation and logging.
 * </ul>
 *
 * <h2>References</h2>
 *
 * <ul>
 *   <li>{@link com.rubensgomes.msbaselib.error.ApplicationErrorCode}
 *   <li>{@link com.rubensgomes.msbaselib.error.ApplicationError}
 *   <li>{@link com.rubensgomes.msbaselib.error.impl.ApplicationErrorCodeImpl}
 *   <li>{@link com.rubensgomes.msbaselib.error.impl.ApplicationErrorImpl}
 * </ul>
 *
 * <p>Last updated: September 25, 2025
 *
 * @author Rubens Gomes
 * @since 0.0.1
 * @see com.rubensgomes.msbaselib.error.impl.ApplicationErrorImpl
 * @see jakarta.validation.constraints
 * @see lombok.Data
 */
package com.rubensgomes.msbaselib.error;
