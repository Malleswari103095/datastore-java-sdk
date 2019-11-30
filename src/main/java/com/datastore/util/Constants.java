package com.datastore.util;

import java.lang.management.ManagementFactory;
import java.nio.file.FileSystems;

public class Constants {

    public static final String FILE_NAME;

    public static final String DEFAULT_DIR_PATH;

    public static final int MAX_CHAR_LENGTH_KEY = 32;
    public static final int MAX_SIZE_VALUE = 16000; // Units in bytes 16 Kb
    public static final int MAX_SIZE_FILE = 1000000000; // Units in bytes 1 Gb

    public static final String PROCESS_NAME;

    static {

        PROCESS_NAME = ManagementFactory.getRuntimeMXBean().getName();
        FILE_NAME = "%s/" + PROCESS_NAME + "@local_store.txt";

        DEFAULT_DIR_PATH = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    }
}
