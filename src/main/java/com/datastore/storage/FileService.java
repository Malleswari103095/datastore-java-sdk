package com.datastore.storage;

import com.datastore.model.StoreValue;
import com.datastore.storage.threads.PersistenceManager;
import com.datastore.util.ObjUtil;
import com.datastore.util.io.FileUtil;
import com.datastore.validator.StoreInputValidator;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.SizeLimitExceededException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.datastore.util.Constants.FILE_NAME;
import static com.datastore.util.Constants.MAX_SIZE_FILE;

public class FileService implements Storage {

    private static StoreInputValidator validator;

    private static String filePath;

    private static Map<String, StoreValue> IN_MEMORY_STORE = new ConcurrentHashMap<>();
    private static long FILE_SIZE = 0;

    public FileService(String dirPath) {

        this.validator = new StoreInputValidator();

        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Path must be a directory");
        }

        filePath = String.format(FILE_NAME, dirPath);

        try {
            File file = new File(filePath);
            if (file.exists()) {
                loadInMemory(filePath);
                return;
            }
            file.createNewFile();

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void loadInMemory(String filePath) throws IllegalAccessException {

        IN_MEMORY_STORE = new FileUtil().readObjectFromFile(filePath);

        FILE_SIZE = ObjUtil.getJson(IN_MEMORY_STORE).getBytes().length;
    }

    @Override
    public Object get(String key) {

        validator.checkKey(key);

        StoreValue storeValue = IN_MEMORY_STORE.get(key);

        if (storeValue != null) {
            if (storeValue.isExpired()) {
                this.remove(key);
                return null;
            }
            return storeValue.getValue();
        }
        return null;
    }

    @Override
    public Object put(String key, Object value) throws SizeLimitExceededException {
        return this.put(key, value, 0);
    }

    @Override
    public Object put(String key, Object value, long expirySec) throws SizeLimitExceededException {

        if (this.get(key) != null)
            throw new KeyAlreadyExistsException("Entry with given key already exists");

        if (FILE_SIZE >= MAX_SIZE_FILE)
            throw new SizeLimitExceededException("File size exceeded the limit");

        validator.checkKey(key);
        validator.checkValue(value);

        Object result = IN_MEMORY_STORE.put(key, new StoreValue(value, expirySec));

        writeToFileAsync();

        return result;
    }

    @Override
    public boolean remove(String key) {

        validator.checkKey(key);

        Object isRemoved = IN_MEMORY_STORE.remove(key);

        if (isRemoved != null)
            writeToFileAsync();

        return isRemoved != null;
    }

    private void writeToFileAsync() {

        Thread thread = new Thread(new PersistenceManager(filePath, IN_MEMORY_STORE));
        thread.start();
    }
}
