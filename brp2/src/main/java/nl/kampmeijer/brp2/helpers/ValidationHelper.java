package nl.kampmeijer.brp2.helpers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class ValidationHelper {

    private ValidationHelper() {}

    /**
     * Validates that the text in the given {@link TextField} is within a specified length range.
     *
     * <p>If the text is invalid (null, shorter than {@code min}, or longer than {@code max}),
     * the provided {@link Label} is updated with the error message and the field gains focus.</p>
     *
     * @param field        the {@link TextField} to validate (must not be null)
     * @param min          the minimum allowed length (inclusive)
     * @param max          the maximum allowed length (inclusive)
     * @param errorMessage the message to display in {@code errorLabel} if validation fails
     * @param errorLabel   the {@link Label} to display the error message
     * @return {@code true} if the field is valid, {@code false} otherwise
     *
     * @throws NullPointerException if {@code field} or {@code errorLabel} is null
     */
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
