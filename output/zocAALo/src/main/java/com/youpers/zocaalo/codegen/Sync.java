package com.youpers.zocaalo.codegen;

/**
 * Class needed to convert JSON structure to POJO for zocAALo specific configuration info in swagger.json
 */

public class Sync {

    private String dbName;
    private int dbVersion;
    private String packageName;
    private String contentAuthority;
    private String accountName;
    private String accountType;
    private boolean useLocalBasePath;
    private String basePathRemoteAddress;
    private String basePathRemotePort;
    private String basePathLocalPort;
    private String author;

    public String getDbName() {
        return dbName;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getContentAuthority() {
        return contentAuthority;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isUseLocalBasePath() {
        return useLocalBasePath;
    }

    public String getBasePathRemoteAddress() {
        return basePathRemoteAddress;
    }

    public String getBasePathRemotePort() {
        return basePathRemotePort;
    }

    public String getBasePathLocalPort() {
        return basePathLocalPort;
    }
}
