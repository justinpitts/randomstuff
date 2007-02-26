
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
        //TempFileReader.log.debug(null);                
        return super.read(cbuf, offset, length);
    }

    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(TempFileReader.class);

    private File file;

    public TempFileReader(final File in) throws FileNotFoundException
    {
        super(new FileInputStream(in));
        this.file = in;
        this.file.deleteOnExit();
        TempFileReader.log.debug(in);
    }

    @Override
    public void close() throws IOException
    {
        if(this.file != null)
        {
            TempFileReader.log.debug(this.file);
            super.close();
            while(this.file.exists())
            {
             TempFileReader.log.debug(this.file.delete());
            }
            this.file = null;            
        }
    }
}
