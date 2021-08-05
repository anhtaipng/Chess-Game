package vn.gihot.chess.admin.core;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements  PasswordEncoder{

    @Value("${chess.password.encoder.secret}")
    private String secret;

    @Value("${chess.password.encoder.iteration}")
    private int iteration;

    @Value("${chess.password.encoder.keylength}")
    private int keylength;

    /**
     * Encoding password to store to DB
     * @param charSequence password
     * @return encoded password
     */
    @Override
    public String encode(CharSequence charSequence) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(charSequence.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            Base64.getEncoder().encodeToString(result);
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence) == s;
    }

}
