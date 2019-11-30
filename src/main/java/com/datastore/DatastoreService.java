package com.datastore;

import com.datastore.storage.FileService;
import com.datastore.storage.Storage;

import static com.datastore.util.Constants.DEFAULT_DIR_PATH;

public class DatastoreService {

    private static Storage instance;

    public static Storage init() {
        return DatastoreService.init(DEFAULT_DIR_PATH);
    }

    public static Storage init(String dirPath) {
        return instance != null ? instance : (instance = new FileService(dirPath));
//        return new DatastoreService(dirPath);
    }
}
