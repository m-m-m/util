/* $Id$ */
package net.sf.mmm.util.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.mmm.util.StringUtil;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FileUtil {

    /**
     * This inner class represents a segment of a glob-matching path. It is a
     * simple container for a string and a pattern.
     */
    private static class PathSegment {

        /** the pattern */
        private Pattern pattern;

        /** the string */
        private String string;

        /**
         * @see java.lang.Object#toString()
         * 
     */
        @Override
        public String toString() {

            return this.string;
        }
    }

    /**
     * The constructor.
     */
    private FileUtil() {

        super();
    }

    /**
     * This method gets all {@link File files} matching to the given
     * <code>path</code> and <code>fileType</code>. The <code>path</code>
     * may be a {@link StringUtil#compileGlobPattern(String) glob-pattern}.<br>
     * Examples:
     * <ul>
     * <li>
     * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, "*", {@link FileType#DIRECTORY})</code>
     * will return all {@link File#isDirectory() directories} in
     * <code>cwd</code> </li>
     * <li>
     * <code>{@link #getMatchingFiles(File, String, FileType) getMatchingFiles}(cwd, "*&#47;*.xml", {@link FileType#FILE})</code>
     * will return all {@link File#isFile() files} from all
     * {@link File#list() subdirectories} of <code>cwd</code> that end with
     * ".xml" </li>
     * </ul>
     * 
     * @param cwd
     *        is the current working directory and should therefore point to an
     *        existing {@link File#isDirectory() directory}. If the given
     *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it
     *        is interpreted relative to this directory.
     * @param path
     *        is the path the requested files must match. If this path is NOT
     *        {@link File#isAbsolute() absolute} it is interpreted relative to
     *        the {@link File#isDirectory() directory} given by <code>cwd</code>.
     * @param fileType
     *        is the type of the requested files or <code>null</code> if files
     *        of any type are acceptable.
     * @return an array containing all the {@link File files} that match the
     *         given <code>path</code> and apply to <code>ignore</code>
     */
    public static File[] getMatchingFiles(File cwd, String path, FileType fileType) {

        List<File> list = new ArrayList<File>();
        collectMatchingFiles(cwd, path, fileType, list);
        return list.toArray(new File[list.size()]);
    }

    /**
     * This method adds all files matching to the given <code>path</code> and
     * <code>fileType</code> to the <code>list</code>. The
     * <code>path</code> may be a
     * {@link StringUtil#compileGlobPattern(String) glob-pattern}
     * 
     * @param cwd
     *        is the current working directory and should therefore point to an
     *        existing {@link File#isDirectory() directory}. If the given
     *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it
     *        is interpreted relative to this directory.
     * @param path
     *        is the path the files to collect must match. If this path is NOT
     *        {@link File#isAbsolute() absolute} it is interpreted relative to
     *        the {@link File#isDirectory() directory} given by <code>cwd</code>.
     * @param fileType
     *        is the type of the files to collect or <code>null</code> if
     *        files of any type are acceptable.
     * @param list
     *        is the list where to {@link List#add(Object) add} the collected
     *        files.
     * @return <code>false</code> if the path is a regular string and
     *         <code>true</code> if the given path is a
     *         {@link StringUtil#compileGlobPattern(String) glob-pattern},
     *         meaning that it contained at least one of the characters '*' or
     *         '?'.
     */
    public static boolean collectMatchingFiles(File cwd, String path, FileType fileType,
            List<File> list) {

        if ((path == null) || (path.length() == 0)) {
            throw new IllegalArgumentException("Path must not be emtpy");
        }
        List<PathSegment> segmentList = new ArrayList<PathSegment>();
        // TODO init cwd according to absolute or relative path
        boolean pathIsPattern = tokenizePath(path, segmentList);
        PathSegment[] segments = segmentList.toArray(new PathSegment[segmentList.size()]);
        collectMatchingFiles(cwd, segments, 0, fileType, list);
        return pathIsPattern;
    }

    /**
     * This method adds all files matching to the given <code>path</code> and
     * <code>fileType</code> to the <code>list</code>. The
     * <code>path</code> may be a
     * {@link StringUtil#compileGlobPattern(String) glob-pattern}
     * 
     * @param cwd
     *        is the current working directory and should therefore point to an
     *        existing {@link File#isDirectory() directory}. If the given
     *        <code>path</code> is NOT {@link File#isAbsolute() absolute} it
     *        is interpreted relative to this directory.
     * @param segments
     *        is the path the files to collect must match. If this path is NOT
     *        {@link File#isAbsolute() absolute} it is interpreted relative to
     *        the {@link File#isDirectory() directory} given by <code>cwd</code>.
     * @param segmentIndex
     *        is the current index in <code>pathChars</code> for the
     *        collection process.
     * @param fileType
     *        is the type of the files to collect or <code>null</code> if
     *        files of any type are acceptable.
     * @param list
     *        is the list where to {@link List#add(Object) add} the collected
     *        files.
     */
    private static void collectMatchingFiles(File cwd, PathSegment[] segments, int segmentIndex,
            FileType fileType, List<File> list) {

        boolean lastSegment;
        if ((segmentIndex + 1) < segments.length) {
            lastSegment = false;
        } else {
            lastSegment = true;
        }
        Pattern pattern = segments[segmentIndex].pattern;
        if (pattern == null) {
            File newCwd = new File(cwd, segments[segmentIndex].string);
            if (newCwd.exists()) {
                if (lastSegment) {
                    if ((fileType == null) || (FileType.getType(newCwd) == fileType)) {
                        list.add(newCwd);
                    }
                } else if (newCwd.isDirectory()) {
                    collectMatchingFiles(newCwd, segments, segmentIndex + 1, fileType, list);
                }
            }
        } else {
            FileType filterType = fileType;
            if (!lastSegment) {
                filterType = FileType.DIRECTORY;
            }
            FileFilter filter = new PatternFileFilter(pattern, filterType);
            File[] children = cwd.listFiles(filter);
            if (lastSegment) {
                for (File file : children) {
                    list.add(file);
                }
            } else {
                for (File file : children) {
                    collectMatchingFiles(file, segments, segmentIndex + 1, fileType, list);
                }
            }
        }
    }

    /**
     * This method tokenized the given <code>path</code> by adding
     * {@link PathSegment}s to the given <code>list</code>.
     * 
     * @param path
     *        is the path to tokenized
     * @param list
     *        is the list where to add the segement tokens.
     * @return <code>true</code> if the path is a glob-pattern (contains '*'
     *         or '?'), <code>false</code> otherwise.
     */
    private static boolean tokenizePath(String path, List<PathSegment> list) {

        char[] pathChars = path.toCharArray();
        int segmentStartIndex = 0;
        int currentIndex = 0;
        boolean pathIsPattern = false;
        boolean segmentIsPattern = false;
        char c = 0;
        while (currentIndex < pathChars.length) {
            c = pathChars[currentIndex++];
            if ((c == '/') || (c == '\\')) {
                int length = currentIndex - segmentStartIndex - 1;
                if (length == 0) {
                    throw new IllegalArgumentException("Duplicate separator in path!");
                }
                PathSegment segment = new PathSegment();
                segment.string = new String(pathChars, segmentStartIndex, length);
                if (segmentIsPattern) {
                    segment.pattern = StringUtil.compileGlobPattern(segment.string);
                } else {
                    segment.pattern = null;
                }
                list.add(segment);
                segmentStartIndex = currentIndex;
                segmentIsPattern = false;
            }
            if ((c == '*') || (c == '?')) {
                segmentIsPattern = true;
                pathIsPattern = true;
            }
        }
        if ((c == '/') || (c == '\\')) {
            // allow this in any case?
        } else {
            PathSegment segment = new PathSegment();
            int length = currentIndex - segmentStartIndex;
            segment.string = new String(pathChars, segmentStartIndex, length);
            if (segmentIsPattern) {
                segment.pattern = StringUtil.compileGlobPattern(segment.string);
            } else {
                segment.pattern = null;
            }
            list.add(segment);
        }
        return pathIsPattern;
    }

    /**
     * The main method to run this class.
     * 
     * @param args
     *        are the command-line arguments.
     */
    public static void main(String[] args) {

        File cwd = new File(System.getProperty("user.home"));
        File[] files = getMatchingFiles(cwd, "*[0-9]/*.xml", FileType.FILE);
        for (File file : files) {
            System.out.println(file);
        }
    }

}
