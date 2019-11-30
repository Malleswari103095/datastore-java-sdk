package com.datastore.validator;

import com.datastore.util.ObjUtil;
import org.json.JSONObject;

import static com.datastore.util.Constants.MAX_CHAR_LENGTH_KEY;
import static com.datastore.util.Constants.MAX_SIZE_VALUE;

public class StoreInputValidator {

    private void checkErrorCase(boolean expression, String errorMsg) {
        if (expression)
            throw new IllegalArgumentException(errorMsg);
    }

    public void checkKey(String key) {

        checkErrorCase(ObjUtil.isBlank(key), "Key must not be null/empty");
        checkErrorCase(key.length() > MAX_CHAR_LENGTH_KEY, "Key length must not exceed 32 characters" );
    }

    public void checkValue(Object value) {

        checkErrorCase(value != null && !(value instanceof JSONObject), "Value must be a JSON Object");

        checkErrorCase(value != null && ((JSONObject) value).toString().getBytes().length > MAX_SIZE_VALUE, "Value length must not exceed 16 kb");
    }
}
