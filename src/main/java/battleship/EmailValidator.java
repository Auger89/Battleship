package battleship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Auger on 01/06/2017.
 * Validates if email direction is well-written
 */
public class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     * @param hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}
