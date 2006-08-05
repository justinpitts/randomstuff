package svnindex;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryFactory
{
	static SVNRepository getRepo() throws SVNException
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

}