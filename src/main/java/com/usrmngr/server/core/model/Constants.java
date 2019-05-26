package com.usrmngr.server.core.model;

import java.io.File;

public final class Constants {
    public static final String APP_NAME = "UserManager";
    public static final String DEFAULT_SERVER_NAME = "localhost";
    public static final String    DEFAULT_LISTEN_PORT = "7777";
    public static final String   DEFAULT_SESSION_TIMEOUT = "60";
    public static final String DEFAULT_CONFIG_FILE_NAME = "configs.txt";
    public static final String DEFAULT_APP_CONFIG_PATH = System.getProperty("user.home") +
            File.separator.concat(".").concat(APP_NAME.toLowerCase()).concat(File.separator);
    public static final String DEFAULT_FULL_CONFIG_FILE_PATH = DEFAULT_APP_CONFIG_PATH.concat(DEFAULT_CONFIG_FILE_NAME);
}
