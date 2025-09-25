/*
 * Copyright 2025 Rubens Gomes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
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
package com.rubensgomes.msbaselib.error.impl;

import com.rubensgomes.msbaselib.error.ApplicationErrorCode;

/**
 * Enumeration of standardized application-level error codes for microservices architecture.
 *
 * <p>This enum implements {@link com.rubensgomes.msbaselib.error.ApplicationErrorCode} and provides
 * a comprehensive set of hierarchically organized error codes commonly encountered in enterprise
 * microservices applications. Each error code includes both a unique prefixed identifier for
 * programmatic handling and a human-readable description for user communication.
 *
 * <p>Error codes are organized into logical categories with consistent prefixes:
 *
 * <ul>
 *   <li><strong>BUSGN###</strong> - Generic Business Logic Errors: Domain-specific constraint
 *       violations and invalid operation states
 *   <li><strong>PAYGN###</strong> - Generic Payment Errors: Financial transaction and payment
 *       processing related errors
 *   <li><strong>RESGN###</strong> - Generic Resource Management: Resource access, availability, and
 *       quota management errors
 *   <li><strong>SECGN###</strong> - Generic Security Errors: Authentication, authorization, and
 *       session management failures
 *   <li><strong>SYSGN###</strong> - Generic System Errors: Infrastructure, database, and external
 *       service failures
 *   <li><strong>VALGN###</strong> - Generic Validation Errors: Input validation, format checking,
 *       and data integrity violations
 *   <li><strong>XXXMS###</strong> - Microservice Specific: Service-specific error codes (e.g.,
 *       USRMS for user management service)
 * </ul>
 *
 * <p>The hierarchical naming convention provides several benefits:
 *
 * <ul>
 *   <li><strong>Easy Categorization</strong> - Errors can be grouped and handled by category
 *   <li><strong>Consistent Error Handling</strong> - Enables programmatic mapping and reporting
 *   <li><strong>Scalability</strong> - Supports addition of new error categories and codes
 * </ul>
 *
 * <h2>Integration Notes</h2>
 *
 * <ul>
 *   <li>Use these error codes for consistent error reporting in microservices and API responses.
 *   <li>Integrate with monitoring and logging systems for error tracking.
 *   <li>Reference these codes in documentation and support materials.
 * </ul>
 *
 * <p>Last updated: September 25, 2025
 *
 * @author Rubens Gomes
 * @since 0.0.1
 * @see com.rubensgomes.msbaselib.error.ApplicationErrorCode
 */
public enum ApplicationErrorCodeImpl implements ApplicationErrorCode {

  // =============================================================================
  // GENERIC (GN) - BUSINESS LOGIC ERRORS (BUS###)
  // =============================================================================
  /**
   * Business rule constraint violation. Used when an operation violates domain-specific business
   * rules.
   */
  BUSINESS_RULE_VIOLATION("BUSGN001", "The operation violates a business rule constraint"),

  /**
   * Invalid operation state. Used when an operation cannot be performed due to current
   * object/system state.
   */
  BUSINESS_INVALID_OPERATION_STATE(
      "BUSGN002", "The operation cannot be performed in the current state"),

  // =============================================================================
  // GENERIC (GN) - PAYMENT ERRORS (PAY###)
  // =============================================================================
  /**
   * Insufficient funds for transaction. Used when a financial operation cannot be completed due to
   * lack of funds.
   */
  PAYMENT_INSUFFICIENT_FUNDS("PAYGN001", "Insufficient funds to complete " + "the operation"),

  // =============================================================================
  // GENERIC (GN) - RESOURCE MANAGEMENT ERRORS (RES###)
  // =============================================================================
  /**
   * Requested resource not found. Used when a requested resource (entity, file, endpoint) does not
   * exist.
   */
  RESOURCE_NOT_FOUND("RESGN001", "The requested resource could not be found"),

  /**
   * Resource temporarily unavailable. Used when a resource exists but is currently unavailable due
   * to maintenance, etc.
   */
  RESOURCE_UNAVAILABLE("RESGN002", "The requested resource is temporarily unavailable"),

  /**
   * Resource state conflict. Used when an operation conflicts with the current state of a resource.
   */
  RESOURCE_CONFLICT("RESGN003", "The operation conflicts with the current resource " + "state"),

  /** Usage quota exceeded. Used when user/application has exceeded their allocated quota. */
  RESOURCE_QUOTA_EXCEEDED("RESGN004", "Usage quota has been exceeded"),

  /** Rate limit exceeded. Used when too many requests have been made within a time window. */
  RESOURCE_RATE_LIMIT_EXCEEDED("RESGN005", "Too many requests - please try" + " again later"),

  // =============================================================================
  // GENERIC (GN) - SECURITY ERRORS (SEC###)
  // =============================================================================
  /**
   * Unauthorized access attempt. Used when authentication is required but not provided or invalid.
   */
  SECURITY_UNAUTHORIZED_ACCESS("SECGN001", "Access denied - authentication " + "required"),

  /** Forbidden operation. Used when user is authenticated but lacks sufficient permissions. */
  SECURITY_FORBIDDEN_OPERATION("SECGN002", "Access denied - insufficient " + "permissions"),

  /**
   * Invalid authentication credentials. Used when provided credentials (username/password, token)
   * are invalid.
   */
  SECURITY_INVALID_CREDENTIALS("SECGN003", "The provided credentials are not" + " valid"),

  /** Session expired. Used when user session has timed out and re-authentication is required. */
  SECURITY_SESSION_EXPIRED("SECGN004", "Your session has expired - please login " + "again"),

  // =============================================================================
  // GENERIC (GN) - SYSTEM ERRORS (SYS###)
  // =============================================================================
  /** Internal server error. Used for unexpected system failures and unhandled exceptions. */
  SYSTEM_INTERNAL_SERVER_ERROR("SYSGN001", "An unexpected error occurred"),

  /**
   * Service unavailable. Used when the service is temporarily down for maintenance or overloaded.
   */
  SYSTEM_SERVICE_UNAVAILABLE("SYSGN002", "The service is temporarily " + "unavailable"),

  /**
   * Database operation failure. Used when database operations fail (connection, query, transaction
   * issues).
   */
  SYSTEM_DATABASE_ERROR("SYSGN003", "A database operation failed"),

  /** External service error. Used when calls to external APIs or services fail. */
  SYSTEM_EXTERNAL_SERVICE_ERROR("SYSGN004", "An external service returned" + " an error"),

  /** System configuration error. Used when system configuration issues are detected. */
  SYSTEM_CONFIGURATION_ERROR("SYSGN005", "A system configuration error was " + "detected"),

  // =============================================================================
  // GENERIC (GN) - VALIDATION ERRORS (VAL###)
  // =============================================================================
  /** Required field missing or empty. Used when mandatory fields are not provided or are empty. */
  VALIDATION_REQUIRED_FIELD("VALGN001", "A required field is missing or empty"),

  /**
   * Invalid value format. Used when field values don't match expected format (email, phone, etc.).
   */
  VALIDATION_INVALID_FORMAT("VALGN002", "The provided value format is not valid"),

  /**
   * Value outside acceptable range. Used when numeric values or dates are outside allowed ranges.
   */
  VALIDATION_OUT_OF_RANGE("VALGN003", "The provided value is outside the acceptable range"),

  /**
   * Duplicate value constraint violation. Used when unique constraints are violated (duplicate
   * email, username, etc.).
   */
  VALIDATION_DUPLICATE_VALUE("VALGN004", "The provided value already exists and must be unique"),

  /** Unsupported file format. Used when uploaded files are not in supported formats. */
  VALIDATION_INVALID_FILE_FORMAT("VALGN005", "The uploaded file format is " + "not supported"),

  /** File size exceeds limit. Used when uploaded files exceed maximum allowed size. */
  VALIDATION_FILE_TOO_LARGE("VALGN006", "The uploaded file exceeds the maximum " + "size limit"),

  /** Data corruption detected. Used when data integrity checks fail or corruption is detected. */
  VALIDATION_DATA_CORRUPTION("VALGN007", "Data corruption was detected"),

  // =============================================================================
  // MICROSERVICE-SPECIFIC ERRORS
  // =============================================================================

  // User Management Service (USRMS###)
  /**
   * User account already exists. Used when attempting to create a user account that already exists.
   */
  USERMGR_MS_ACCOUNT_EXISTS(
      "USRMS001", "Cannot create user account because one " + "already exists");

  private final String code;
  private final String description;

  /**
   * Constructs an ApplicationErrorCode with the specified code and description.
   *
   * @param code the unique error code identifier
   * @param description the human-readable error description
   */
  ApplicationErrorCodeImpl(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * {@inheritDoc}
   *
   * @return the error code identifier, never null or blank
   */
  @Override
  public String getCode() {
    return code;
  }

  /**
   * {@inheritDoc}
   *
   * @return the error description, never null or blank
   */
  @Override
  public String getDescription() {
    return description;
  }
}
