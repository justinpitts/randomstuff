
package com.randomhumans.svnindex.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//TODO: spring!
public class Configuration
{
    static Log log = LogFactory.getLog(Configuration.class);

    static
    {
        try
        {
            
            System.getProperties().load(Configuration.class.getClassLoader().getResourceAsStream("svnindex.properties"));
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
    
    private String[] ignoredExtensions;

    private String pathToRepository;

    private int directoryEntryThreadPoolPoolSize;
    
    private String[] folders; 

    public String[] getFolders()
    {    
        return folders.clone();
    }

    public void setFolders(String[] folders)
    {
        this.folders = folders.clone();
    }

    public String getPathToRepository()
    {
        return this.pathToRepository;
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
        c.pathToRepository = System.getProperty("com.randomhumans.svnindex.repoURL");
        c.directoryEntryThreadPoolPoolSize = Integer.parseInt(System.getProperty("com.randomhumans.svnindex.DirectoryEntryThreadPool.poolSize"));
        c.folders=System.getProperty("com.randomhumans.svnindex.folders").split(",");
        c.ignoredExtensions =  System.getProperty("com.randomhumans.svnindex.ignoredExtensions").split(",");
        return c;
    }

    public String[] getIgnoredNames()
    {
        return this.ignoredNames.clone();
    }

    public void setIgnoredNames(final String[] ignoredNames)
    {
        this.ignoredNames = ignoredNames.clone();
    }



    public int getDirectoryEntryThreadPoolPoolSize()
    {
        return this.directoryEntryThreadPoolPoolSize;
    }

    public void setDirectoryEntryThreadPoolPoolSize(final int directoryEntryThreadPoolPoolSize)
    {
        this.directoryEntryThreadPoolPoolSize = directoryEntryThreadPoolPoolSize;
    }


    public String[] getIgnoredExtensions()
    {
        return ignoredExtensions.clone();
    }

    public void setIgnoredExtensions(String[] ignoredExtensions)
    {
        this.ignoredExtensions = ignoredExtensions.clone();
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString()
    {
        final String TAB = "    ";
        
        String retValue = "";
        
        retValue = "Configuration ( "
            + super.toString() + TAB
            + "indexLocation = " + this.indexLocation + TAB
            + "ignoredNames = " + Arrays.deepToString(this.ignoredNames) + TAB
            + "ignoredExtensions = " + Arrays.deepToString(this.ignoredExtensions) + TAB
            + "repositoryURL = " + this.pathToRepository + TAB
            + "directoryEntryThreadPoolPoolSize = " + this.directoryEntryThreadPoolPoolSize + TAB
            + "folders = " + Arrays.deepToString(this.folders) + TAB
            + " )";
    
        return retValue;
    }


}
