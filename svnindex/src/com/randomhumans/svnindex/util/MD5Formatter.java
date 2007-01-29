
package com.randomhumans.svnindex.util;

public class MD5Formatter
{

    public static String formatHash(final byte[] hash)
    {
        final StringBuffer sb = new StringBuffer();
        String hexDigit;
        for (final byte b : hash)
        {
            hexDigit = "0" + Integer.toHexString(b);
            sb.append(hexDigit.substring(hexDigit.length() - 2));
        }
        return sb.toString();
    }
}
