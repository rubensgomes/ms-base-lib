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
package com.rubensgomes.msbaselib.error;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ApplicationErrorCode Interface Tests")
class ApplicationErrorCodeTest {

  static class TestErrorCode implements ApplicationErrorCode {
    public String getCode() {
      return "TEST_CODE";
    }

    public String getDescription() {
      return "Test description";
    }
  }

  @Test
  void testCodeAndDescriptionAreNonBlank() {
    ApplicationErrorCode code = new TestErrorCode();
    assertNotNull(code.getCode());
    assertFalse(code.getCode().isBlank());
    assertNotNull(code.getDescription());
    assertFalse(code.getDescription().isBlank());
  }

  @Test
  void testCodeAndDescriptionValues() {
    ApplicationErrorCode code = new TestErrorCode();
    assertEquals("TEST_CODE", code.getCode());
    assertEquals("Test description", code.getDescription());
  }
}
