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
        String url = "http://svn.cns.com/repo";
        return url;
    }

    public String getRepoPassword()
    {
    	String password = "ng7#mht";
    	return password;
    }

    public String getRepoUser()
    {
    	String user = "nextgenbuilder";
    	return user;
    }

    public String getIndexLocation()
    {
        return System.getProperty("com.randomhumans.svnindex.indexLocation", "z:/index");
    }

}
