/* $ Id: $ */
package net.sf.mmm.util.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

import net.sf.mmm.util.StringUtil;

/**
 * This is the implementation of a {@link FilenameFilter} that filters using a
 * {@link java.util.regex.Pattern pattern}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PatternFileFilter implements FileFilter {

    /**
     * the pattern that must match in order to {@link #accept(File) accept} a
     * file by its {@link File#getName() name}.
     */
    private final Pattern pattern;

    /**
     * The type of the {@link #accept(File) acceptable} files or
     * <code>null</code> if any type is acceptable.
     */
    private final FileType fileType;

    /**
     * The constructor.
     * 
     * @param filenamePattern
     *        is the pattern that must match in order to
     *        {@link #accept(File) accept} a file by its
     *        {@link File#getName() name}.
     */
    public PatternFileFilter(Pattern filenamePattern) {

        this(filenamePattern, null);
    }

    /**
     * The constructor.
     * 
     * @param filenamePattern
     *        is the pattern that must match in order to
     *        {@link #accept(File) accept} a file by its
     *        {@link File#getName() name}.
     * @param type
     *        the type of the {@link #accept(File) acceptable} files or
     *        <code>null</code> if any type is acceptable.
     */
    public PatternFileFilter(Pattern filenamePattern, FileType type) {

        super();
        this.pattern = filenamePattern;
        this.fileType = type;
    }

    /**
     * The constructor.
     * 
     * @param filenamePattern
     *        is the {@link StringUtil#compileGlobPattern(String) glob-pattern}
     *        that must match in order to {@link #accept(File) accept} a file by
     *        its {@link File#getName() name}.
     * @param type
     *        the type of the {@link #accept(File) acceptable} files or
     *        <code>null</code> if any type is acceptable.
     */
    public PatternFileFilter(String filenamePattern, FileType type) {

        this(StringUtil.compileGlobPattern(filenamePattern), type);
    }

    /**
     * @see java.io.FileFilter#accept(java.io.File)
     * 
     */
    public boolean accept(File file) {

        if (this.fileType != null) {
            if (this.fileType != FileType.getType(file)) {
                return false;
            }
        }
        return this.pattern.matcher(file.getName()).matches();
    }

}
