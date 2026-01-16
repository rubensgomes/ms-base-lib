/*
 * Copyright 2026 Rubens Gomes
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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.rubensgomes.msbaselib.error.ApplicationErrorCode;

@DisplayName("ApplicationErrorCodeImpl Enum Tests")
class ApplicationErrorCodeImplTest {

  @Test
  void testAllEnumValuesHaveNonBlankCodeAndDescription() {
    for (ApplicationErrorCodeImpl code : ApplicationErrorCodeImpl.values()) {
      assertNotNull(code.getCode(), "Error code must not be null");
      assertFalse(code.getCode().isBlank(), "Error code must not be blank");
      assertNotNull(code.getDescription(), "Description must not be null");
      assertFalse(code.getDescription().isBlank(), "Description must not be blank");
    }
  }

  @Test
  void testSpecificEnumValue() {
    ApplicationErrorCode code = ApplicationErrorCodeImpl.BUSINESS_RULE_VIOLATION;
    assertEquals("BUSGN001", code.getCode());
    assertEquals("The operation violates a business rule constraint", code.getDescription());
  }
}
