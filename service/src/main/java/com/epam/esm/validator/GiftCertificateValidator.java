package com.epam.esm.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * The type Custom validator.
 */
@Component
public class GiftCertificateValidator {

    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private static final String REGEX_CORRECT_TAG_ID = "[0-9]+";

    /**
     * Is sorted type boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean isSortedType(String type){
        return type != null && (type.equalsIgnoreCase(ASC) || type.equalsIgnoreCase(DESC));
    }

    public boolean isTagId(String[] stringsTagId){
        return stringsTagId != null && Arrays.stream(stringsTagId).allMatch(s -> s.matches(REGEX_CORRECT_TAG_ID));
    }
}
