/* $Id$ */
package net.sf.mmm.util.io;

import java.io.File;

/**
 * This enum contains the available types of a {@link java.io.File file}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum FileType {

    /** represents a {@link java.io.File#isDirectory() file} */
    DIRECTORY,

    /** represents a {@link java.io.File#isFile() plain file} */
    FILE;

    /**
     * This method determines the type of a file.
     * 
     * @param file
     *        is the file to check.
     * @return {@link #DIRECTORY} if the given <code>file</code> is a
     *         {@link File#isDirectory() directory}, {@link #FILE} for a
     *         {@link File#isFile() plain file} and <code>null</code>
     *         otherwise.
     */
    public static FileType getType(File file) {

        if (file.isDirectory()) {
            return DIRECTORY;
        } else if (file.isFile()) {
            return FILE;
        } else {
            return null;
        }
    }

}
