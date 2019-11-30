package com.datastore;

import com.datastore.storage.Storage;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.SizeLimitExceededException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DatastoreServiceTest extends TestBase {

    private static Storage storage;

    @BeforeClass
    public static void beforeClass() {

        System.out.println("====Tests Begin====");
        storage = DatastoreService.init();
    }

    @Test
    public void getSingletonInstanceTest() {

        int firstInstanceHashcode = storage.hashCode();

        assertEquals(firstInstanceHashcode, DatastoreService.init().hashCode());
        assertEquals(firstInstanceHashcode, DatastoreService.init().hashCode());
        assertEquals(firstInstanceHashcode, DatastoreService.init("/Users/malleswari/Documents").hashCode());
    }

    @Test
    public void putValidKeyValueTest() throws SizeLimitExceededException {

        JSONObject map = new JSONObject();
        map.put("key", "value");

        assertNull(storage.put("one", map));
        assertNull(storage.put("two", map));

        assertNotNull(storage.get("one"));

        assertNotNull(storage.remove("two"));
    }

    @Test
    public void putInvalidKeyValueTest() throws Exception {

        expectIllegalArgException(() -> {storage.put("array", Arrays.asList("1", "2"));}, "Value must be a JSON Object", null);
        expectIllegalArgException(() -> {storage.put("", null);}, "Key must not be null/empty", null);
        expectIllegalArgException(() -> {storage.put("1234567890123456789012345678901234567890", null);}, "Key length must not exceed 32 characters", null);
    }

    @Test
    public void getValidKeyTest() {

        assertNull(storage.get("x"));
    }

    @Test
    public void expireableKeysTest() throws SizeLimitExceededException, InterruptedException {

        JSONObject map = new JSONObject();
        map.put("key", "value");

        assertNull(storage.put("one", map, 1));
        assertNotNull(storage.get("one"));

        Thread.sleep(1001);

        assertNull(storage.get("one"));

    }

    @AfterClass
    public static void afterClass() {

        System.out.println("====Tests End====");
        storage = null;
    }


}