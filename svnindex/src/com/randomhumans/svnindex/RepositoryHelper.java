package com.randomhumans.svnindex;

import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryHelper
{
	private static SVNRepository getRepo() throws SVNException
	{
		DAVRepositoryFactory.setup();
		SVNRepository repository = null;
		repository = SVNRepositoryFactory.create(SVNURL
				.parseURIEncoded(getRepoURL()));
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(getRepoUser(),
						getRepoPassword());
		repository.setAuthenticationManager(authManager);
		return repository;
	}

	public static long getLatestRevision() throws SVNException 
	{
		SVNRepository repo = null;
		try
		{
			repo = getRepo();
			return repo.getLatestRevision();
		} finally {
			repo.closeSession();
		}
	}
	private static String getRepoPassword()
	{
		String password = "gir@ffe";
		return password;
	}

	private static String getRepoUser()
	{
		String user = "jpitts";
		return user;
	}

	private static String getRepoURL()
	{
		String url = "http://svn.cns.com/repo";
		return url;
	}

	@SuppressWarnings("unchecked")
	public static SVNLogEntry getLogEntry(long revisionNumber)
	{
		SVNRepository repo = null;
		SVNLogEntry logEntry  = null;
		try {
		try
		{
			repo = getRepo();
			ArrayList<SVNLogEntry> logs;
			try
			{
				logs = new ArrayList(repo.log(new String[] {""}, null, revisionNumber, revisionNumber, true, true));
				logEntry = logs.get(0);
	
			} catch (SVNException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} catch (SVNException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logEntry;
		} finally {
			try
			{
				repo.closeSession();
			} catch (SVNException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
