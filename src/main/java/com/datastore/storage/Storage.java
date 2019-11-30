package com.datastore.storage;

import javax.naming.SizeLimitExceededException;

public interface Storage {

    Object get(String key);

    Object put(String key, Object value) throws SizeLimitExceededException;

    Object put(String key, Object value, long expirySec) throws SizeLimitExceededException;

    boolean remove(String key);

}
