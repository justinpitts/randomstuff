package com.randomhumans.svnindex.util;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

public interface RepositoryAction<T>
{
    public T execute(SVNRepository repo) throws SVNException;
}
