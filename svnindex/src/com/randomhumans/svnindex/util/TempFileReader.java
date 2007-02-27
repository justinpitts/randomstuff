
package com.randomhumans.svnindex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TempFileReader extends InputStreamReader
{
    @Override
    public int read(final char[] cbuf, final int offset, final int length) throws IOException
    {
        return super.read(cbuf, offset, length);
    }

    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(TempFileReader.class);

    private File file;

    public TempFileReader(final File in) throws FileNotFoundException
    {
        super(new FileInputStream(in));
        this.file = in;
        this.file.deleteOnExit();
    }

    @Override
    public void close() throws IOException
    {
        int i = 0;
        if (this.file != null)
        {
            super.close();
            while (this.file.exists() && i < 5)
            {
                this.file.delete();
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    log.warn(e);
                }
                i++;
            }
            if (file.exists())
            {
                log.debug("abandoned delete of " + file.toString());
            }
            this.file = null;
        }
    }
}
