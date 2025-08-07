package com.ch.secret;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("ShaHashingSecret Test")
class ShaHashingSecretTest {

  @Mock
  private SecretLogger securityLogger;

  private ShaHashingSecret shaHashingSecret;

  private static final String PLAIN_TEXT = "testPassword123";
  private static final String DIFFERENT_PLAIN_TEXT = "differentPassword123";
  private static final String SALT = "testSalt";
  private static final String DIFFERENT_SALT = "differentSalt";
  private static final String BLANK = "";

  /**
   * SHA3-256 해싱을 제공하는 구현체 설정<br>
   * Reflection 이용한 환경 변수 systemSalt 설정
   * @see ShaHashingSecret
   */
  @BeforeEach
  void secretSetUp(){
    shaHashingSecret = new ShaHashingSecret(securityLogger);
    ReflectionTestUtils.setField(shaHashingSecret, "systemSalt", "hashing-test-salt");
  }

  @Nested
  @DisplayName("솔트 없이 해싱하는 encrypt를 진행 시")
  class Describe_Encrypt {

    @Nested
    @DisplayName("유효하지 않은 평문이")
    class Context_InCorrect_PlainText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encrypt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encrypt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 평문이 오면")
    class Context_Correct_PlainText {

      @Test
      @DisplayName("정상적으로 Hashing을 통해 HashedText 반환합니다.")
      void It_HashedText_When_Correct_PlainText() {
        // when
        String hashedText = shaHashingSecret.encrypt(PLAIN_TEXT);

        // then
        assertThat(hashedText).isNotNull().isBase64();
      }
    }
  }

  @Nested
  @DisplayName("솔트 없는 verify를 진행 시")
  class Describe_Verify {

    @Nested
    @DisplayName("유효하지 않은 해시값이")
    class Context_InCorrect_HashedText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_HashedText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.verify(PLAIN_TEXT, BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_HashedText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.verify(PLAIN_TEXT, null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 평문과 해시값이 오면")
    class Context_Correct_PlainTextAndHashedText {

      @Test
      @DisplayName("해싱 값의 평문과 유효한 평문이 같다면 true를 반환합니다.")
      void It_Return_True_When_Equals_PlainTextAndHashedText() {
        //given
        String hashedText = shaHashingSecret.encrypt(PLAIN_TEXT);

        //when
        boolean expectedTrue = shaHashingSecret.verify(PLAIN_TEXT, hashedText);

        //then
        assertThat(expectedTrue).isTrue();
      }

      @Test
      @DisplayName("해싱 값의 평문과 유효한 평문이 다른면 false를 반환합니다.")
      void It_Return_False_When_NotEquals_PlainTextAndHashedText() {
        //given
        String hashedText = shaHashingSecret.encrypt(PLAIN_TEXT);

        //when
        boolean expectedFalse = shaHashingSecret.verify(DIFFERENT_PLAIN_TEXT, hashedText);

        //then
        assertThat(expectedFalse).isFalse();
      }
    }
  }



  @Nested
  @DisplayName("솔트가 존재하는 encrypt를 진행 시")
  class Describe_EncryptWithSalt {
    @Nested
    @DisplayName("유효하지 않은 평문이")
    class Context_InCorrect_PlainText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSalt(BLANK, SALT))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSalt(null, SALT))
            .isInstanceOf(SecurityException.class);
      }


    }
    @Nested
    @DisplayName("유효하지 않은 솔트가 ")
    class Context_InCorrect_Salt {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_Salt() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSalt(PLAIN_TEXT, BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_Salt() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSalt(PLAIN_TEXT, null))
            .isInstanceOf(SecurityException.class);
      }

    }

    @Nested
    @DisplayName("유효한 평문과 솔트가 오면")
    class Context_Correct_PlainTextAndSalt {

      @Test
      @DisplayName("정상적으로 Hashing을 진행합니다.")
      void It_Return_HashedText() {
        // when
        String hashedText = shaHashingSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        // then
        assertThat(hashedText).isNotNull().isBase64();
      }
    }
  }

  @Nested
  @DisplayName("솔트가존재하는 verify를 진행 시")
  class Describe_VerifyWithSalt {

    @Nested
    @DisplayName("유효하지 않은 해싱값이")
    class Context_InCorrect_HashedText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_HashedText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.verifyWithSalt(PLAIN_TEXT, BLANK, SALT))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_HashedText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.verifyWithSalt(PLAIN_TEXT, null, SALT))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 해싱 값이")
    class Context_Correct_HashedText {

      @Test
      @DisplayName("해싱값과 유효한 평문이 같지만, 솔트가 같으면 true를 반환합니다.")
      void It_Return_True_When_Correct_HashedText() {
        //given
        String hashedText = shaHashingSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        // when
        boolean expectedTrue = shaHashingSecret.verifyWithSalt(PLAIN_TEXT, hashedText, SALT);

        // then
        assertThat(expectedTrue).isTrue();
      }

      @Test
      @DisplayName("해싱값과 유효한 평문이 같지만, 솔트가 다르면 false를 반환합니다.")
      void It_Return_False_When_Incorrect_Salt() {
        //given
        String hashedText = shaHashingSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        // when
        boolean expectedFalse = shaHashingSecret.verifyWithSalt(PLAIN_TEXT, hashedText, DIFFERENT_SALT);

        // then
        assertThat(expectedFalse).isFalse();
      }

      @Test
      @DisplayName("해싱값과 유효한 평문이 다르면 false를 반환합니다.")
      void It_Return_False_When_Incorrect_PlainText() {
        //given
        String hashedText = shaHashingSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        // when
        boolean expectedFalse = shaHashingSecret.verifyWithSalt(DIFFERENT_PLAIN_TEXT, hashedText, SALT);

        // then
        assertThat(expectedFalse).isFalse();
      }
    }
  }


  @Nested
  @DisplayName("시스템 솔트를 사용하여 해싱하는 encrypt를 진행 시")
  class Describe_EncryptWithSystemSalt {

    @Nested
    @DisplayName("유효하지 않은 평문이")
    class Context_InCorrect_PlainText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSystemSalt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> shaHashingSecret.encryptWithSystemSalt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 평문이 오면")
    class Context_Correct_PlainText {

      @Test
      @DisplayName("정상적으로 Hashing을 진행 및 HashedText 반환합니다.")
      void It_Return_HashedText() {
        // when
        String hashedText = shaHashingSecret.encryptWithSystemSalt(PLAIN_TEXT);

        // then
        assertThat(hashedText).isNotNull().isBase64();
      }
    }
    @Nested
    @DisplayName("시스템 솔트를 사용하여 해싱하는 verify를 진행 시")
    class Describe_VerifyWithSystemSalt {

      @Nested
      @DisplayName("유효하지 않은 해싱값이")
      class Context_InCorrect_HashedText {

        @Test
        @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
        void It_Throws_SecurityException_When_Blank_HashedText() {
          // when & then
          assertThatThrownBy(() -> shaHashingSecret.verifyWithSystemSalt(PLAIN_TEXT, BLANK))
              .isInstanceOf(SecurityException.class);
        }

        @Test
        @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
        void It_Throws_SecurityException_When_Null_HashedText() {
          // when & then
          assertThatThrownBy(() -> shaHashingSecret.verifyWithSystemSalt(PLAIN_TEXT, null))
              .isInstanceOf(SecurityException.class);
        }
      }

      @Nested
      @DisplayName("유효한 해싱값이 오면")
      class Context_Correct_HashedText {

        @Test
        @DisplayName("해싱값과 유효한 평문이 같다면 true를 반환합니다.")
        void It_Return_True_When__HashedText() {
          //given
          String hashedText = shaHashingSecret.encryptWithSystemSalt(PLAIN_TEXT);

          // when
          boolean expectedTrue = shaHashingSecret.verifyWithSystemSalt(PLAIN_TEXT, hashedText);

          // then
          assertThat(expectedTrue).isTrue();
        }

        @Test
        @DisplayName("해싱값과 유효한 평문이 다르면 false를 반환합니다.")
        void It_Return_False_When_Incorrect_PlainText() {
          //given
          String hashedText = shaHashingSecret.encryptWithSystemSalt(PLAIN_TEXT);

          // when
          boolean expectedFalse = shaHashingSecret.verifyWithSystemSalt(DIFFERENT_PLAIN_TEXT, hashedText);

          // then
          assertThat(expectedFalse).isFalse();
        }
      }
    }
  }
  @Test
  @DisplayName("SHA3-256 암호화를 위한 랜덤 솔트 생성")
  void It_Generate_Random_Salt() {
    // when
    String salt = shaHashingSecret.generateSalt();

    // then
    assertThat(salt).isNotNull().isBase64();
  }
}