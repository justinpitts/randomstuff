
package com.randomhumans.svnindex.util;

public class Configuration
{
    public static Configuration getConfig()
    {
        return Configuration.loadFromSystemProperties();
    }

    private String indexLocation;

    private String[] ignoredNames;

    public String getRepositoryURL()
    {
        return System.getProperty("com.randomhumans.svnindex.repoURL");
    }

    public String getRepoPassword()
    {
        final String password = "";
        return password;
    }

    public String getRepoUser()
    {
        final String user = "";
        return user;
    }

    public String getIndexLocation()
    {
        return this.indexLocation;
    }

    public static Configuration loadFromSystemProperties()
    {
        final Configuration c = new Configuration();
        c.indexLocation = System.getProperty("com.randomhumans.svnindex.indexLocation");

        return c;
    }

    public String[] getIgnoredNames()
    {
        return this.ignoredNames;
    }

    public void setIgnoredNames(final String[] ignoredNames)
    {
        this.ignoredNames = ignoredNames;
    }

}
