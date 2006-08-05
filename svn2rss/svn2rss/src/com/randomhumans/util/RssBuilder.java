package com.randomhumans.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.ecs.html.A;
import org.apache.ecs.html.I;
import org.apache.ecs.html.P;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import viecili.jrss.generator.RSSFeedGenerator;
import viecili.jrss.generator.RSSFeedGeneratorFactory;
import viecili.jrss.generator.elem.Channel;
import viecili.jrss.generator.elem.Item;
import viecili.jrss.generator.elem.RSS;

public class RssBuilder {

	
	public static void main(String[] args) throws SVNException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		SVNRepository repository = getRepository();
		Collection<SVNLogEntry> logs = getLastNLogs(repository, 100);
		RSSFeedGenerator generator = RSSFeedGeneratorFactory.getDefault();
		
		RSS rss = new RSS(RSS.VERSION_2_0);
		Channel c = new Channel(getChannelName(), getRepoURL(), "Subversion Repo Log Feed");
		rss.addChannel(c);		
		for (SVNLogEntry entry : logs) {
			Item i = svnLogtoRssItem(entry);
			c.addItem(i);
			if (entry.getRevision() % 100 == 0)
				System.out.print(".");
		}
		
		generator.generateToFile(rss, new File("c:/training/svn.xml"));

	}

	@SuppressWarnings("unchecked")
	private static Collection<SVNLogEntry> getLastNLogs(SVNRepository repository, int count ) throws SVNException {
		long end = repository.getLatestRevision();
		long start = end - count;		
		Collection<SVNLogEntry> logs = repository.log(new String[] { "" },null, start, end, true, true);
		return logs;
	}

	private static String getChannelName() {
		return "Subversion Revision feed";
	}

	private static SVNRepository getRepository() throws SVNException {
		DAVRepositoryFactory.setup();
		SVNRepository repository = null;
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(getRepoURL()));
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(getRepoUser(), getRepoPassword());
		repository.setAuthenticationManager(authManager);
		return repository;
	}

	private static String getRepoPassword() {
		String password = "gir@ffe";
		return password;
	}

	private static String getRepoUser() {
		String user = "jpitts";
		return user;
	}

	private static String getRepoURL() {
		String url = "http://svn.cns.com/repo";
		return url;
	}

	private static Item svnLogtoRssItem(SVNLogEntry entry) {
		Item i = new Item(Long.toString(entry.getRevision()),"stuff", buildDescription(entry) );
		i.setAuthor(entry.getAuthor());
		i.setPubDate(entry.getDate());		
		return i;
	}

	private static String buildDescription(SVNLogEntry entry)
	{
		P para = new P();
		P commitMessage = new P();
		commitMessage.addElement(new I(entry.getMessage()));
		para.addElement(commitMessage);
		para.addElement(new P());
		StringBuffer sb = new StringBuffer();
		for(Object s: entry.getChangedPaths().values())
		{
			
			SVNLogEntryPath logEntryPath = (SVNLogEntryPath)s;
			A pathRef =  new A(getRepoURL(), logEntryPath.getPath());
			P line = new P();
			
			sb.append(logEntryPath.getType()).append(" - ").append("<a href=\"http://svn.cns.com/repo/").append(logEntryPath.getPath()).append("\">").append(logEntryPath.getPath()).append("</a><p />");
		}
		return sb.toString();
	}
}
