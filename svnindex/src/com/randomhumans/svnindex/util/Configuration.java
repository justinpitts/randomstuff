
package com.randomhumans.svnindex.util;

public class Configuration
{
    public static Configuration getConfig()
    {
        return loadFromSystemProperties();
    }

    private String indexLocation;

    private String[] ignoredNames;

    public String getRepositoryURL()
    {
        return System.getProperty("com.randomhumans.svnindex.repoURL");
    }

    public String getRepoPassword()
    {
        String password = "";
        return password;
    }

    public String getRepoUser()
    {
        String user = "";
        return user;
    }

    public String getIndexLocation()
    {
        return indexLocation;
    }

    public static Configuration loadFromSystemProperties()
    {
        Configuration c = new Configuration();
        c.indexLocation = System.getProperty("com.randomhumans.svnindex.indexLocation");

        return c;
    }

    public String[] getIgnoredNames()
    {
        return ignoredNames;
    }

    public void setIgnoredNames(String[] ignoredNames)
    {
        this.ignoredNames = ignoredNames;
    }

}
