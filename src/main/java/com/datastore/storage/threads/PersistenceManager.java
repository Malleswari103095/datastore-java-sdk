package com.datastore.storage.threads;

import com.datastore.util.io.FileUtil;

public class PersistenceManager implements Runnable {

    String filePath;
    Object content;

    public PersistenceManager(String filePath, Object content) {
        this.filePath = filePath;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            new FileUtil().writeObjectToFile(this.filePath, this.content);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
