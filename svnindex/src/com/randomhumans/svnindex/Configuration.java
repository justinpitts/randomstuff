package com.randomhumans.svnindex;

public class Configuration
{
    private static Configuration singleton = null;
    public static Configuration getConfig()
    {
        return singleton;
    }
    
    static {
        singleton = new Configuration();
        
    }
    private Configuration()
    {
        
    }
    
    public String getRepositoryURL()
    {
        String url = "http://randomstuff.googlecode.com/svn";
        return url;
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
        return System.getProperty("com.randomhumans.svnindex.indexLocation", "d:/index");
    }

}
