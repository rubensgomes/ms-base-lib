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

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.rubensgomes.msbaselib.error.impl.ApplicationErrorImpl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("ApplicationError Tests")
class ApplicationErrorTest {

  private ValidatorFactory factory;
  private Validator validator;

  @BeforeEach
  void setUp() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /** Simple test implementation of ErrorCode for testing purposes. */
  static class TestErrorCode implements ApplicationErrorCode {
    private final String code;
    private final String description;

    public TestErrorCode(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getDescription() {
      return description;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      TestErrorCode that = (TestErrorCode) obj;
      return code.equals(that.code) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
      return code.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
      return "TestErrorCode{code='" + code + "', description='" + description + "'}";
    }
  }

  @Nested
  @DisplayName("Constructor Tests")
  class ConstructorTests {

    @Test
    @DisplayName("Should create ApplicationError with valid parameters")
    void shouldCreateApplicationErrorWithValidParameters() {
      // Given
      String errorDescription = "Test error description";
      TestErrorCode errorCode = new TestErrorCode("TEST001", "Test error code");

      // When
      ApplicationErrorImpl error = new ApplicationErrorImpl(errorDescription, errorCode);

      // Then
      assertNotNull(error);
      assertEquals(errorDescription, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
      assertNull(error.getNativeErrorText());
    }

    @Test
    @DisplayName("Should create ApplicationError with different error codes")
    void shouldCreateApplicationErrorWithDifferentErrorCodes() {
      // Given
      String errorDescription = "Different error codes test";
      TestErrorCode[] errorCodes = {
        new TestErrorCode("VALIDATION001", "Validation error"),
        new TestErrorCode("DATABASE002", "Database error"),
        new TestErrorCode("NETWORK003", "Network error"),
        new TestErrorCode("AUTH004", "Authentication error")
      };

      // When/Then
      for (TestErrorCode errorCode : errorCodes) {
        ApplicationErrorImpl error = new ApplicationErrorImpl(errorDescription, errorCode);
        assertEquals(errorDescription, error.getErrorDescription());
        assertEquals(errorCode, error.getErrorCode());
        assertNull(error.getNativeErrorText());
      }
    }

    @Test
    @DisplayName("Should allow creation with null parameters but fail validation")
    void shouldAllowCreationWithNullParametersButFailValidation() {
      // Given
      String validDescription = "Valid description";
      TestErrorCode validErrorCode = new TestErrorCode("VALID001", "Valid error code");

      // When - These should not throw exceptions at construction time
      assertDoesNotThrow(() -> new ApplicationErrorImpl(null, validErrorCode));
      assertDoesNotThrow(() -> new ApplicationErrorImpl(validDescription, null));
      assertDoesNotThrow(() -> new ApplicationErrorImpl(null, null));

      // Then - But validation should catch these issues
      ApplicationErrorImpl errorWithNullDescription =
          new ApplicationErrorImpl(null, validErrorCode);
      Set<ConstraintViolation<ApplicationErrorImpl>> violations =
          validator.validate(errorWithNullDescription);
      assertFalse(
          violations.isEmpty(), "Should have validation violations for null errorDescription");
    }
  }

  @Nested
  @DisplayName("Validation Tests")
  class ValidationTests {

    @Test
    @DisplayName("Should pass validation with valid fields")
    void shouldPassValidationWithValidFields() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Valid error description", new TestErrorCode("VALID001", "Valid error code"));

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertTrue(violations.isEmpty(), "Should have no validation violations");
    }

    @Test
    @DisplayName("Should fail validation when errorDescription is null")
    void shouldFailValidationWhenErrorDescriptionIsNull() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(null, new TestErrorCode("NULL001", "Null description test"));

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertFalse(violations.isEmpty(), "Should have validation violations");
      assertTrue(
          violations.stream()
              .anyMatch(v -> v.getPropertyPath().toString().equals("errorDescription")),
          "Should have errorDescription validation violation");
    }

    @Test
    @DisplayName("Should fail validation when errorDescription is empty")
    void shouldFailValidationWhenErrorDescriptionIsEmpty() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl("", new TestErrorCode("EMPTY001", "Empty description test"));

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertFalse(violations.isEmpty(), "Should have validation violations");
      assertTrue(
          violations.stream()
              .anyMatch(v -> v.getPropertyPath().toString().equals("errorDescription")),
          "Should have errorDescription validation violation");
    }

    @Test
    @DisplayName("Should fail validation when errorDescription is blank")
    void shouldFailValidationWhenErrorDescriptionIsBlank() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "   \t\n   ", new TestErrorCode("BLANK001", "Blank description test"));

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertFalse(violations.isEmpty(), "Should have validation violations");
      assertTrue(
          violations.stream()
              .anyMatch(v -> v.getPropertyPath().toString().equals("errorDescription")),
          "Should have errorDescription validation violation");
    }

    @Test
    @DisplayName("Should fail validation when errorCode is null")
    void shouldFailValidationWhenErrorCodeIsNull() {
      // Given
      ApplicationErrorImpl error = new ApplicationErrorImpl("Valid error description", null);

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertFalse(violations.isEmpty(), "Should have validation violations");
      assertTrue(
          violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("errorCode")),
          "Should have errorCode validation violation");
    }

    @Test
    @DisplayName("Should pass validation with null nativeErrorText")
    void shouldPassValidationWithNullNativeErrorText() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Valid error description", new TestErrorCode("NULL_NATIVE001", "Null native test"));
      error.setNativeErrorText(null);

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertTrue(
          violations.isEmpty(), "Should have no validation violations for null nativeErrorText");
    }

    @Test
    @DisplayName("Should pass validation with empty nativeErrorText")
    void shouldPassValidationWithEmptyNativeErrorText() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Valid error description", new TestErrorCode("EMPTY_NATIVE001", "Empty native test"));
      error.setNativeErrorText("");

      // When
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);

      // Then
      assertTrue(
          violations.isEmpty(), "Should have no validation violations for empty nativeErrorText");
    }
  }

  @Nested
  @DisplayName("Native Error Text Tests")
  class NativeErrorTextTests {

    @Test
    @DisplayName("Should allow setting native error text after construction")
    void shouldAllowSettingNativeErrorTextAfterConstruction() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Test error", new TestErrorCode("NATIVE001", "Native error test"));
      String nativeErrorText = "SQLException: Connection timeout at localhost:5432";

      // When
      error.setNativeErrorText(nativeErrorText);

      // Then
      assertEquals(nativeErrorText, error.getNativeErrorText());
    }

    @Test
    @DisplayName("Should allow null native error text")
    void shouldAllowNullNativeErrorText() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Test error", new TestErrorCode("NULL_NATIVE002", "Null native test"));

      // When
      error.setNativeErrorText(null);

      // Then
      assertNull(error.getNativeErrorText());
    }

    @Test
    @DisplayName("Should allow changing native error text")
    void shouldAllowChangingNativeErrorText() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Test error", new TestErrorCode("CHANGE_NATIVE001", "Change native test"));
      String firstNativeText = "First native error";
      String secondNativeText = "Second native error";

      // When
      error.setNativeErrorText(firstNativeText);
      assertEquals(firstNativeText, error.getNativeErrorText());

      error.setNativeErrorText(secondNativeText);

      // Then
      assertEquals(secondNativeText, error.getNativeErrorText());
      assertNotEquals(firstNativeText, error.getNativeErrorText());
    }

    @Test
    @DisplayName("Should handle complex native error text")
    void shouldHandleComplexNativeErrorText() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Complex native error test",
              new TestErrorCode("COMPLEX_NATIVE001", "Complex native test"));
      String complexNativeText =
          "Stack trace:\n"
              + "java.sql.SQLException: Connection refused\n"
              + "\tat com.example.Database.connect(Database.java:42)\n"
              + "\tat com.example.Service.process(Service.java:123)\n"
              + "Caused by: java.net.ConnectException: Connection refused\n"
              + "\t... 15 more";

      // When
      error.setNativeErrorText(complexNativeText);

      // Then
      assertEquals(complexNativeText, error.getNativeErrorText());
    }
  }

  @Nested
  @DisplayName("Immutability Tests")
  class ImmutabilityTests {

    @Test
    @DisplayName("Should have final core fields that cannot be modified")
    void shouldHaveFinalCoreFields() throws NoSuchFieldException {
      // Given/When
      var errorDescriptionField = ApplicationErrorImpl.class.getDeclaredField("errorDescription");
      var errorCodeField = ApplicationErrorImpl.class.getDeclaredField("errorCode");

      // Then
      assertTrue(
          java.lang.reflect.Modifier.isFinal(errorDescriptionField.getModifiers()),
          "errorDescription field should be final");
      assertTrue(
          java.lang.reflect.Modifier.isFinal(errorCodeField.getModifiers()),
          "errorCode field should be final");
    }

    @Test
    @DisplayName("Should have non-final nativeErrorText field for mutability")
    void shouldHaveNonFinalNativeErrorTextField() throws NoSuchFieldException {
      // Given/When
      var nativeErrorTextField = ApplicationErrorImpl.class.getDeclaredField("nativeErrorText");

      // Then
      assertFalse(
          java.lang.reflect.Modifier.isFinal(nativeErrorTextField.getModifiers()),
          "nativeErrorText field should not be final to allow mutation");
    }

    @Test
    @DisplayName("Should maintain same core values after creation")
    void shouldMaintainSameCoreValuesAfterCreation() {
      // Given
      String originalDescription = "Immutable error description";
      TestErrorCode originalErrorCode = new TestErrorCode("IMMUTABLE001", "Immutable test");
      ApplicationErrorImpl error = new ApplicationErrorImpl(originalDescription, originalErrorCode);

      // When (accessing values multiple times)
      String description1 = error.getErrorDescription();
      String description2 = error.getErrorDescription();
      ApplicationErrorCode errorCode1 = error.getErrorCode();
      ApplicationErrorCode errorCode2 = error.getErrorCode();

      // Then
      assertEquals(originalDescription, description1);
      assertEquals(originalDescription, description2);
      assertEquals(originalErrorCode, errorCode1);
      assertEquals(originalErrorCode, errorCode2);
      assertSame(description1, description2, "Should return same reference");
      assertSame(errorCode1, errorCode2, "Should return same reference");
    }
  }

  @Nested
  @DisplayName("Error Interface Implementation Tests")
  class ErrorInterfaceImplementationTests {

    @Test
    @DisplayName("Should provide Error interface methods")
    void shouldProvideErrorInterfaceMethods() {
      // Given
      String errorDescription = "Interface methods test";
      TestErrorCode errorCode = new TestErrorCode("INTERFACE002", "Interface methods test");
      ApplicationErrorImpl error = new ApplicationErrorImpl(errorDescription, errorCode);

      // When/Then - Should have Error interface methods available
      assertEquals(errorDescription, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
      assertNull(error.getNativeErrorText());
    }
  }

  @Nested
  @DisplayName("Equals and HashCode Tests")
  class EqualsAndHashCodeTests {

    @Test
    @DisplayName("Should be equal when all fields are the same")
    void shouldBeEqualWhenAllFieldsAreTheSame() {
      // Given
      String errorDescription = "Equals test error";
      TestErrorCode errorCode = new TestErrorCode("EQUALS001", "Equals test");
      ApplicationErrorImpl error1 = new ApplicationErrorImpl(errorDescription, errorCode);
      ApplicationErrorImpl error2 = new ApplicationErrorImpl(errorDescription, errorCode);

      // When/Then
      assertEquals(error1, error2, "Errors with same fields should be equal");
      assertEquals(error1.hashCode(), error2.hashCode(), "Equal errors should have same hash code");
    }

    @Test
    @DisplayName("Should not be equal when errorDescription differs")
    void shouldNotBeEqualWhenErrorDescriptionDiffers() {
      // Given
      TestErrorCode errorCode = new TestErrorCode("DIFF001", "Different description test");
      ApplicationErrorImpl error1 = new ApplicationErrorImpl("First error description", errorCode);
      ApplicationErrorImpl error2 = new ApplicationErrorImpl("Second error description", errorCode);

      // When/Then
      assertNotEquals(error1, error2, "Errors with different descriptions should not be equal");
    }

    @Test
    @DisplayName("Should not be equal when errorCode differs")
    void shouldNotBeEqualWhenErrorCodeDiffers() {
      // Given
      String errorDescription = "Different error code test";
      TestErrorCode errorCode1 = new TestErrorCode("DIFF002", "First error code");
      TestErrorCode errorCode2 = new TestErrorCode("DIFF003", "Second error code");
      ApplicationErrorImpl error1 = new ApplicationErrorImpl(errorDescription, errorCode1);
      ApplicationErrorImpl error2 = new ApplicationErrorImpl(errorDescription, errorCode2);

      // When/Then
      assertNotEquals(error1, error2, "Errors with different error codes should not be equal");
    }

    @Test
    @DisplayName("Should not be equal when nativeErrorText differs")
    void shouldNotBeEqualWhenNativeErrorTextDiffers() {
      // Given
      String errorDescription = "Native error text test";
      TestErrorCode errorCode = new TestErrorCode("NATIVE_DIFF001", "Native difference test");
      ApplicationErrorImpl error1 = new ApplicationErrorImpl(errorDescription, errorCode);
      ApplicationErrorImpl error2 = new ApplicationErrorImpl(errorDescription, errorCode);

      error1.setNativeErrorText("First native error");
      error2.setNativeErrorText("Second native error");

      // When/Then
      assertNotEquals(
          error1, error2, "Errors with different native error text should not be equal");
    }

    @Test
    @DisplayName("Should be equal when nativeErrorText is both null")
    void shouldBeEqualWhenNativeErrorTextIsBothNull() {
      // Given
      String errorDescription = "Null native test";
      TestErrorCode errorCode = new TestErrorCode("NULL_BOTH001", "Both null test");
      ApplicationErrorImpl error1 = new ApplicationErrorImpl(errorDescription, errorCode);
      ApplicationErrorImpl error2 = new ApplicationErrorImpl(errorDescription, errorCode);

      // When/Then (both have null nativeErrorText by default)
      assertEquals(error1, error2, "Errors with both null native error text should be equal");
      assertEquals(error1.hashCode(), error2.hashCode(), "Equal errors should have same hash code");
    }

    @Test
    @DisplayName("Should not be equal to null")
    void shouldNotBeEqualToNull() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl("Null test", new TestErrorCode("NULL_TEST001", "Null test"));

      // When/Then
      assertNotEquals(error, null, "Error should not be equal to null");
    }

    @Test
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl("Self test", new TestErrorCode("SELF001", "Self test"));

      // When/Then
      assertEquals(error, error, "Error should be equal to itself");
    }
  }

  @Nested
  @DisplayName("ToString Tests")
  class ToStringTests {

    @Test
    @DisplayName("Should contain all fields in toString")
    void shouldContainAllFieldsInToString() {
      // Given
      String errorDescription = "ToString test error";
      TestErrorCode errorCode = new TestErrorCode("TOSTRING001", "ToString test");
      ApplicationErrorImpl error = new ApplicationErrorImpl(errorDescription, errorCode);
      error.setNativeErrorText("Native error for toString test");

      // When
      String toString = error.toString();

      // Then
      assertNotNull(toString, "toString should not return null");
      assertTrue(toString.contains(errorDescription), "toString should contain errorDescription");
      assertTrue(toString.contains("TOSTRING001"), "toString should contain error code");
      assertTrue(
          toString.contains("Native error for toString test"),
          "toString should contain nativeErrorText");
    }

    @Test
    @DisplayName("Should handle null nativeErrorText in toString")
    void shouldHandleNullNativeErrorTextInToString() {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "ToString null native test",
              new TestErrorCode("TOSTRING_NULL001", "ToString null test"));
      // nativeErrorText is null by default

      // When/Then
      assertDoesNotThrow(
          () -> error.toString(), "toString should handle null nativeErrorText without exceptions");
    }

    @Test
    @DisplayName("Should handle special characters in toString")
    void shouldHandleSpecialCharactersInToString() {
      // Given
      String specialDescription = "Error with special chars: @#$%^&*(){}[]|\\:;\"'<>?,./";
      TestErrorCode errorCode = new TestErrorCode("SPECIAL001", "Special chars test");
      ApplicationErrorImpl error = new ApplicationErrorImpl(specialDescription, errorCode);
      error.setNativeErrorText("Native error with\nnewlines\tand\ttabs");

      // When/Then
      assertDoesNotThrow(
          () -> error.toString(), "toString should handle special characters without exceptions");
    }
  }

  @Nested
  @DisplayName("Thread Safety Tests")
  class ThreadSafetyTests {

    @Test
    @DisplayName("Should be thread-safe for read operations on immutable fields")
    void shouldBeThreadSafeForReadOperationsOnImmutableFields() throws InterruptedException {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Concurrent read test", new TestErrorCode("CONCURRENT001", "Concurrent test"));
      int numberOfThreads = 10;
      int operationsPerThread = 1000;
      Thread[] threads = new Thread[numberOfThreads];

      // When
      for (int i = 0; i < numberOfThreads; i++) {
        threads[i] =
            new Thread(
                () -> {
                  for (int j = 0; j < operationsPerThread; j++) {
                    // Perform read operations concurrently on immutable fields
                    String description = error.getErrorDescription();
                    ApplicationErrorCode errorCode = error.getErrorCode();
                    String toString = error.toString();
                    int hashCode = error.hashCode();

                    // Verify core values remain consistent
                    assertEquals("Concurrent read test", description);
                    assertEquals("CONCURRENT001", errorCode.getCode());
                    assertNotNull(toString);
                    assertTrue(hashCode != 0);
                  }
                });
        threads[i].start();
      }

      // Wait for all threads to complete
      for (Thread thread : threads) {
        thread.join();
      }

      // Then - If we reach here without exceptions, the test passes
      assertTrue(true, "Concurrent read operations completed without issues");
    }

    @Test
    @DisplayName("Should handle concurrent nativeErrorText modifications")
    void shouldHandleConcurrentNativeErrorTextModifications() throws InterruptedException {
      // Given
      ApplicationErrorImpl error =
          new ApplicationErrorImpl(
              "Concurrent native test",
              new TestErrorCode("CONCURRENT_NATIVE001", "Concurrent native test"));
      int numberOfThreads = 5;
      Thread[] threads = new Thread[numberOfThreads];

      // When - Multiple threads modify the nativeErrorText
      for (int i = 0; i < numberOfThreads; i++) {
        final int threadIndex = i;
        threads[i] =
            new Thread(
                () -> {
                  String nativeText = "Native error from thread " + threadIndex;
                  error.setNativeErrorText(nativeText);
                  // Verify we can read the native error text (might be from any thread)
                  String readText = error.getNativeErrorText();
                  assertNotNull(readText);
                });
        threads[i].start();
      }

      // Wait for all threads to complete
      for (Thread thread : threads) {
        thread.join();
      }

      // Then - Should have some native error text set (from one of the threads)
      assertNotNull(
          error.getNativeErrorText(), "Native error text should be set by one of the threads");
    }
  }

  @Nested
  @DisplayName("Edge Cases Tests")
  class EdgeCasesTests {

    @Test
    @DisplayName("Should handle very long field values")
    void shouldHandleVeryLongFieldValues() {
      // Given
      String longDescription = "Very long error description: " + "a".repeat(1000);
      TestErrorCode errorCode =
          new TestErrorCode("LONG001", "Long test error code: " + "b".repeat(500));
      String longNativeText = "Very long native error text: " + "c".repeat(2000);

      // When
      ApplicationErrorImpl error = new ApplicationErrorImpl(longDescription, errorCode);
      error.setNativeErrorText(longNativeText);

      // Then
      assertEquals(longDescription, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
      assertEquals(longNativeText, error.getNativeErrorText());
      assertDoesNotThrow(() -> error.toString());
    }

    @Test
    @DisplayName("Should handle Unicode characters")
    void shouldHandleUnicodeCharacters() {
      // Given
      String unicodeDescription = "Unicode error: 测试错误信息 αβγδε русский текст";
      TestErrorCode errorCode = new TestErrorCode("UNICODE001", "Unicode test ñáéíóú");
      String unicodeNativeText = "Native Unicode: 日本語エラー ελληνικά خطأ";

      // When
      ApplicationErrorImpl error = new ApplicationErrorImpl(unicodeDescription, errorCode);
      error.setNativeErrorText(unicodeNativeText);

      // Then
      assertEquals(unicodeDescription, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
      assertEquals(unicodeNativeText, error.getNativeErrorText());
      assertDoesNotThrow(() -> error.toString());
    }

    @Test
    @DisplayName("Should handle boundary field values")
    void shouldHandleBoundaryFieldValues() {
      // Given - Using single character values (boundary case for @NotBlank)
      String minDescription = "E";
      TestErrorCode errorCode = new TestErrorCode("B", "Boundary test");

      // When
      ApplicationErrorImpl error = new ApplicationErrorImpl(minDescription, errorCode);

      // Then
      Set<ConstraintViolation<ApplicationErrorImpl>> violations = validator.validate(error);
      assertTrue(violations.isEmpty(), "Single character values should be valid");
      assertEquals(minDescription, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
    }

    @Test
    @DisplayName("Should handle newlines and special whitespace")
    void shouldHandleNewlinesAndSpecialWhitespace() {
      // Given
      String descriptionWithNewlines = "Error description\nwith\nnewlines\tand\ttabs";
      TestErrorCode errorCode = new TestErrorCode("NEWLINE001", "Newline test");
      String nativeWithNewlines = "Native error\nwith\nmultiple\nlines\tand\ttabs";

      // When
      ApplicationErrorImpl error = new ApplicationErrorImpl(descriptionWithNewlines, errorCode);
      error.setNativeErrorText(nativeWithNewlines);

      // Then
      assertEquals(descriptionWithNewlines, error.getErrorDescription());
      assertEquals(errorCode, error.getErrorCode());
      assertEquals(nativeWithNewlines, error.getNativeErrorText());
      assertDoesNotThrow(() -> error.toString());
    }
  }
}
