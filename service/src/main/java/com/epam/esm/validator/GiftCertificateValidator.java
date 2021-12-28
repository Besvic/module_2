package com.epam.esm.validator;

import org.springframework.stereotype.Component;
/**
 * The type Custom validator.
 */
@Component
public class GiftCertificateValidator {

    private static final String ASC = "asc";
    private static final String DESC = "desc";

    /**
     * Is sorted type boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean isSortedType(String type){
        return type != null && (type.equalsIgnoreCase(ASC) || type.equalsIgnoreCase(DESC));
    }
}
