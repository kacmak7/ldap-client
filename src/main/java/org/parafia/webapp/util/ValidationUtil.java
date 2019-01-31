package org.parafia.webapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;


/**
 * ValidationUtil Helper class for performing custom validations that
 * aren't already included in the core Commons Validator.
 *
 * <p>
 * <a href="ValidationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ValidationUtil {
	private static Logger log = Logger.getLogger(ValidationUtil.class);
    //~ Methods ================================================================

    /**
     * Validates that two fields match.
     * @param bean
     * @param va
     * @param field
     * @param errors
     */
    public static boolean validateTwoFields(Object bean, ValidatorAction va,
                                            Field field, Errors errors) {
        String value =
            ValidatorUtils.getValueAsString(bean, field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (!value.equals(value2)) {
                    FieldChecks.rejectValue(errors, field, va);
                    return false;
                }
            } catch (Exception e) {
                FieldChecks.rejectValue(errors, field, va);
                return false;
            }
        }

        return true;
    }

	/**
	 * Validates that two fields not match.
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean validateTwoFieldsNotEqual(Object bean,
			ValidatorAction va, Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String sProperty2 = field.getVarValue("secondField");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (value.equals(value2)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	/**
	 * Validates that one date is greater than the other
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean validateDateGreater(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_TEXT,
				Locale.ENGLISH);

		try {
			Date endDate = sdf.parse(ValidatorUtils.getValueAsString(bean,
					field.getProperty()));
			Date startDate = sdf.parse(ValidatorUtils.getValueAsString(bean,
					field.getVarValue("secondDate")));
			if (startDate != null && endDate != null) {
				if (startDate.after(endDate)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} catch (NullPointerException pe) { // ktoras z dat nie jest poprawna,
											// zostanie odrzucona w innym
											// miejscu
			return true;
		} catch (Exception e) {
			log.warn(e);
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}
	}

	/**
	 * Validates that one date is greater than today's date
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean validateDateGrThanToday(Object bean,
			ValidatorAction va, Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String sProperty2 = field.getVarValue("secondDate");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (value.equals(value2)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	/**
	 * Validates that the field is not empty if other field is empty
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean requiredIfOtherNull(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String value2 = ValidatorUtils.getValueAsString(bean, field
				.getVarValue("secondField"));

		if (GenericValidator.isBlankOrNull(value)
				&& GenericValidator.isBlankOrNull(value2)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validates that the field is not empty if other field is not empty
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean requiredIfOtherNotNull(Object bean,
			ValidatorAction va, Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String value2 = ValidatorUtils.getValueAsString(bean, field
				.getVarValue("secondField"));

		if (GenericValidator.isBlankOrNull(value)
				&& !GenericValidator.isBlankOrNull(value2)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validates that the field is empty if other field is not empty
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean requiredNullIfOtherNotNull(Object bean,
			ValidatorAction va, Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String value2 = ValidatorUtils.getValueAsString(bean, field
				.getVarValue("secondField"));

		if (!GenericValidator.isBlankOrNull(value)
				&& !GenericValidator.isBlankOrNull(value2)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		} else {
			return true;
		}
	}
}
