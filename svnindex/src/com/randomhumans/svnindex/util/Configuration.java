
package com.randomhumans.svnindex.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuration
{
    static Log log = LogFactory.getLog(Configuration.class);

    static
    {
        try
        {
            System.getProperties().load(new FileInputStream("config/svnindex.properties"));
        }
        catch (final FileNotFoundException e)
        {
            Configuration.log.error(e);
        }
        catch (final IOException e)
        {
            Configuration.log.error(e);
        }
    }

    public static Configuration getConfig()
    {
        return Configuration.loadFromSystemProperties();
    }

    private String indexLocation;

    private String[] ignoredNames;

    private String repositoryURL;

    private int directoryEntryThreadPoolPoolSize;

    public String getRepositoryURL()
    {
        return this.repositoryURL;
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
        c.setIgnoredNames(System.getProperty("com.randomhumans.svnindex.ignore").split(","));
        c.repositoryURL = System.getProperty("com.randomhumans.svnindex.repoURL");
        c.directoryEntryThreadPoolPoolSize = Integer.parseInt(System.getProperty("com.randomhumans.svnindex.DirectoryEntryThreadPool.poolSize"));
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

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    @Override
    public String toString()
    {
        final String TAB = "\r\n";
        
        String retValue = "";
        
        retValue = "Configuration ( "
            + super.toString() + TAB
            + "indexLocation = " + this.indexLocation + TAB
            + "ignoredNames = " + this.ignoredNames + TAB
            + "repositoryURL = " + this.repositoryURL + TAB
            + " )";
    
        return retValue;
    }

    public int getDirectoryEntryThreadPoolPoolSize()
    {
        return directoryEntryThreadPoolPoolSize;
    }

    public void setDirectoryEntryThreadPoolPoolSize(int directoryEntryThreadPoolPoolSize)
    {
        this.directoryEntryThreadPoolPoolSize = directoryEntryThreadPoolPoolSize;
    }

}
