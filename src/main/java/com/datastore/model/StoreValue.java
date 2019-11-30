package com.datastore.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.Map;

@Data
@NoArgsConstructor
public class StoreValue {

    private Map<String, Object> value;

    private long expiryTimeStamp = 0L;

    public StoreValue(Object value, long expirySec) {

        this.value = ((JSONObject) value).toMap();
        if (expirySec > 0)
            this.expiryTimeStamp = System.currentTimeMillis() + (expirySec * 1000);
    }

    public JSONObject getValue() {
        return this.value != null ? new JSONObject(this.value) : null;
    }

    public boolean isExpired() {
        return this.expiryTimeStamp > 0L && this.expiryTimeStamp < System.currentTimeMillis();
    }
}
