package nl.kampmeijer.brp2.helpers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class ValidationHelper {

    private ValidationHelper() {}

    public static boolean validateLength(
            @NotNull TextField field,
            int min,
            int max,
            String errorMessage,
            Label errorLabel
    ) {
        String value = field.getText();

        if (value == null || value.trim().length() < min || value.trim().length() > max) {
            errorLabel.setText(errorMessage);
            field.requestFocus();
            return false;
        }

        return true;
    }
}
