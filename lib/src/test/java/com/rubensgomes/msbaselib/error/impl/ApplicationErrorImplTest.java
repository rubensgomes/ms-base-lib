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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.rubensgomes.msbaselib.error.ApplicationErrorCode;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ApplicationErrorImpl Tests")
class ApplicationErrorImplTest {

  private Validator validator;

  static class TestErrorCode implements ApplicationErrorCode {
    public String getCode() {
      return "TEST_CODE";
    }

    public String getDescription() {
      return "Test description";
    }
  }

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void testValidConstruction() {
    ApplicationErrorImpl error = new ApplicationErrorImpl("Valid error", new TestErrorCode());
    assertEquals("Valid error", error.getErrorDescription());
    assertEquals("TEST_CODE", error.getErrorCode().getCode());
    assertNull(error.getNativeErrorText());
  }

  @Test
  void testSetNativeErrorText() {
    ApplicationErrorImpl error = new ApplicationErrorImpl("Error", new TestErrorCode());
    error.setNativeErrorText("Native error");
    assertEquals("Native error", error.getNativeErrorText());
  }

  @Test
  void testValidationFailsOnBlankDescription() {
    ApplicationErrorImpl error = new ApplicationErrorImpl("   ", new TestErrorCode());
    Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);
    assertFalse(violations.isEmpty(), "Should fail validation for blank description");
  }

  @Test
  void testValidationFailsOnNullErrorCode() {
    ApplicationErrorImpl error = new ApplicationErrorImpl("Error", null);
    Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);
    assertFalse(violations.isEmpty(), "Should fail validation for null error code");
  }
}
