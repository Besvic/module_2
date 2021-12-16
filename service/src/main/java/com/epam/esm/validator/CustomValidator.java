package com.epam.esm.validator;

import org.springframework.stereotype.Service;

@Service
public class CustomValidator {

    private static final String ASC = "asc";
    private static final String DESC = "desc";

    public boolean isSortedType(String type){
        return type != null && (type.equalsIgnoreCase(ASC) || type.equalsIgnoreCase(DESC));
    }
}
