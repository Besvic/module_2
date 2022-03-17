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
    private static final String REGEX_CORRECT_NAME = "[a-zA-Zа-яА-Я\\s]+";
    private static final String REGEX_CORRECT_DESCRIPTION = "[a-zA-Zа-яА-Я\\s,.!?]+";

    /**
     * Is sorted type boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean isSortedType(String type){
        return type != null && (type.equalsIgnoreCase(ASC) || type.equalsIgnoreCase(DESC));
    }

    /**
     * Is tag id boolean.
     *
     * @param stringsTagId the strings tag id
     * @return the boolean
     */
    public boolean isTagId(String[] stringsTagId){
        return stringsTagId != null && Arrays.stream(stringsTagId).allMatch(s -> s.matches(REGEX_CORRECT_TAG_ID));
    }

    /**
     * Is name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isName(String name){
        return name != null && !name.isEmpty() && name.matches(REGEX_CORRECT_NAME);
    }

    /**
     * Is description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean isDescription(String description){
        return description != null && !description.isEmpty() && description.matches(REGEX_CORRECT_DESCRIPTION);
    }
}

