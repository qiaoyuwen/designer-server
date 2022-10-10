package designer.server.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;

@Component
public class WerkzeugPwdEncoder {

  private final String HASH_METHOD = "PBKDF2WithHmacSHA256";
  private final String WERKZEUG_PREFIX = "pbkdf2:sha256:";
  private final int HASH_WIDTH = 256;

  public String encode(CharSequence rawPassword) {
    try {
      int iterationCount = 5000;
      String salt = RandomStringUtils.randomAlphanumeric(8);
      PBEKeySpec spec = new PBEKeySpec(
          rawPassword.toString().toCharArray(),
          salt.getBytes(), iterationCount, HASH_WIDTH);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_METHOD);
      byte[] encoded = skf.generateSecret(spec).getEncoded();
      return WERKZEUG_PREFIX + iterationCount + "$" + salt + "$" + String.valueOf(Hex.encode(encoded));
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("Could not create hash", e);
    }
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    String[] strArray = encodedPassword.substring(WERKZEUG_PREFIX.length()).split("\\$");
    if (strArray.length != 3) {
      return false;
    }
    PBEKeySpec spec = new PBEKeySpec(
        rawPassword.toString().toCharArray(),
        strArray[1].getBytes(),
        Integer.parseInt(strArray[0]),
        HASH_WIDTH);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_METHOD);
      byte[] encoded = skf.generateSecret(spec).getEncoded();
      String encodedStr = String.valueOf(Hex.encode(encoded));
      return strArray[2].equals(encodedStr);
    } catch (GeneralSecurityException e) {
      return false;
    }
  }
}
