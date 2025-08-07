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
@DisplayName("AES-256 암호화 테스트")
class AesSecretTest {

  @Mock
  SecretLogger secretLogger;
  AesSecret aesSymmetricSecret;

  private static final String PLAIN_TEXT = "testPassword123";
  private static final String SALT = "testSalt";
  private static final String BLANK = "";

  @BeforeEach
  void aesSecretSetup() {
    aesSymmetricSecret = new AesSecret(secretLogger);
    ReflectionTestUtils.setField(aesSymmetricSecret, "secretKey", "dHVzc2xlYy1hZXMtMjU2LWxvY2FsLXNlY3JldC1rZXk=");
    ReflectionTestUtils.setField(aesSymmetricSecret, "systemSalt", "symmetric-test-salt");
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
        assertThatThrownBy(() -> aesSymmetricSecret.encrypt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.encrypt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 평문이 오면")
    class Context_Correct_PlainText {

      @Test
      @DisplayName("정상적으로 암호화를 통해 EncryptedText 반환합니다.")
      void It_EncryptedText() {
        // when
        String encryptedText = aesSymmetricSecret.encrypt(PLAIN_TEXT);

        // then
        assertThat(encryptedText).isNotNull().isBase64();
      }
    }
  }

  @Nested
  @DisplayName("솔트 없는 decrypt를 진행 시")
  class Describe_Decrypt {

    @Nested
    @DisplayName("유효하지 않은 암호문이")
    class Context_InCorrect_EncryptedText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decrypt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decrypt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 암호문이 오면")
    class Context_Correct_DecryptedText {

      @Test
      @DisplayName("복호화를 통해 평문으로 변환 및 반환합니다.")
      void It_Return_DecryptedText() {
        //given
        String encryptedText = aesSymmetricSecret.encrypt(PLAIN_TEXT);

        //when
        String plainText = aesSymmetricSecret.decrypt(encryptedText);

        //then
        assertThat(plainText).isEqualTo(PLAIN_TEXT);
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
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSalt(BLANK, SALT))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSalt(null, SALT))
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
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSalt(PLAIN_TEXT, BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_Salt() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSalt(PLAIN_TEXT, null))
            .isInstanceOf(SecurityException.class);
      }

    }

    @Nested
    @DisplayName("유효한 평문과 솔트가 오면")
    class Context_Correct_PlainTextAndSalt {

      @Test
      @DisplayName("정상적으로 암화를 진행한 뒤 암호문을 반환합니다.")
      void It_Return_EncryptedText() {
        // when
        String encryptedText = aesSymmetricSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        // then
        assertThat(encryptedText).isNotNull().isBase64();
      }
    }
  }

  @Nested
  @DisplayName("솔트 있는 decrypt를 진행 시")
  class Describe_DecryptWithSalt {

    @Nested
    @DisplayName("유효하지 않은 암호문이")
    class Context_InCorrect_EncryptedText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decryptWithSalt(BLANK, SALT))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decryptWithSalt(null, SALT))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 암호문이 오면")
    class Context_Correct_DecryptedText {

      @Test
      @DisplayName("복호화를 통해 평문으로 변환 및 반환합니다.")
      void It_Return_DecryptedText() {
        //given
        String encryptedText = aesSymmetricSecret.encryptWithSalt(PLAIN_TEXT, SALT);

        //when
        String decryptedText = aesSymmetricSecret.decryptWithSalt(encryptedText, SALT);

        //then
        assertThat(decryptedText).isEqualTo(PLAIN_TEXT);
      }
    }
  }

  @Nested
  @DisplayName("시스템 솔트를 사용하여 암호화하는 encrypt를 진행 시")
  class Describe_EncryptWithSystemSalt {

    @Nested
    @DisplayName("유효하지 않은 평문이")
    class Context_InCorrect_PlainText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_PlainText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSystemSalt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_PlainText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.encryptWithSystemSalt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 평문이 오면")
    class Context_Correct_PlainText {

      @Test
      @DisplayName("정상적으로 암호화를 진행한 뒤 암호문을 반환합니다.")
      void It_Return_EncryptedText() {
        // when
        String encryptedText = aesSymmetricSecret.encryptWithSystemSalt(PLAIN_TEXT);

        // then
        assertThat(encryptedText).isNotNull().isBase64();
      }
    }
  }

  @Nested
  @DisplayName("시스템 솔트를 사용해서 decrypt를 진행 시")
  class Describe_DecryptWithSystemSalt {

    @Nested
    @DisplayName("유효하지 않은 암호문이")
    class Context_InCorrect_EncryptedText {

      @Test
      @DisplayName("공백만 있는 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Blank_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decryptWithSystemSalt(BLANK))
            .isInstanceOf(SecurityException.class);
      }

      @Test
      @DisplayName("Null인 문자열이면 SecurityException을 던진다.")
      void It_Throws_SecurityException_When_Null_EncryptedText() {
        // when & then
        assertThatThrownBy(() -> aesSymmetricSecret.decryptWithSystemSalt(null))
            .isInstanceOf(SecurityException.class);
      }
    }

    @Nested
    @DisplayName("유효한 암호문이 오면")
    class Context_Correct_DecryptedText {

      @Test
      @DisplayName("복호화를 통해 평문으로 변환 및 반환합니다.")
      void It_Return_DecryptedText() {
        //given
        String encryptedText = aesSymmetricSecret.encryptWithSystemSalt(PLAIN_TEXT);

        //when
        String decryptedText = aesSymmetricSecret.decryptWithSystemSalt(encryptedText);

        //then
        assertThat(decryptedText).isEqualTo(PLAIN_TEXT);
      }
    }
  }
  @Test
  @DisplayName("SHA3-256 암호화를 위한 랜덤 솔트 생성")
  void It_Generate_Random_Salt() {
    // when
    String salt = aesSymmetricSecret.generateSalt();

    // then
    assertThat(salt).isNotNull().isBase64();
  }
}