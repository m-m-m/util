/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.search.engine.api.config.SearchEntryType;

/**
 * This class contains the {@link #getDefaultTypes() default} {@link SearchEntryType}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class SearchEntryTypeDefaults {

  /**
   * Construction forbidden.
   */
  private SearchEntryTypeDefaults() {

  }

  /** @see #getEntryTypeAny() */
  private static final SearchEntryType ENTRY_TYPE_ANY = new SearchEntryTypeBean(SearchEntryType.ID_ANY, "Any",
      "file.png");

  /**
   * The default types.
   */
  private static final Collection<SearchEntryType> DEFAULT_TYPES;
  static {
    List<SearchEntryType> list = new ArrayList<SearchEntryType>();
    list.add(ENTRY_TYPE_ANY);
    list.add(new SearchEntryTypeBean("html", "HTML", "firefox.png"));
    list.add(new SearchEntryTypeBean("css", "CSS", "css.png"));
    list.add(new SearchEntryTypeBean("pdf", "PDF", "pdf.png"));
    // list.add(new SearchEntryTypeBean("ps", "Postscript", "ps.png"));
    list.add(new SearchEntryTypeBean("rtf", "RTF", "ms-rtf.png"));
    list.add(new SearchEntryTypeBean("doc", "Word", "ms-word.png"));
    list.add(new SearchEntryTypeBean("docx", "Word", "ms-word.png"));
    list.add(new SearchEntryTypeBean("ppt", "Powerpoint", "ms-powerpoint.png"));
    list.add(new SearchEntryTypeBean("pptx", "Powerpoint", "ms-powerpoint.png"));
    list.add(new SearchEntryTypeBean("xls", "Excel", "ms-excel.png"));
    list.add(new SearchEntryTypeBean("xlsx", "Excel", "ms-excel.png"));
    list.add(new SearchEntryTypeBean("bat", "Bat", "ms-dos.png"));
    list.add(new SearchEntryTypeBean("exe", "Exe", "exe.png"));
    list.add(new SearchEntryTypeBean("java", "Java", "java.png"));
    // list.add(new SearchEntryTypeBean("js", "JavaScript", "todo.png"));
    list.add(new SearchEntryTypeBean("jsp", "JSP", "java.png"));
    list.add(new SearchEntryTypeBean("php", "PHP", "php.png"));
    list.add(new SearchEntryTypeBean("py", "Python", "py.png"));
    list.add(new SearchEntryTypeBean("sql", "SQL", "sql.png"));
    list.add(new SearchEntryTypeBean("vmdk", "VMDK", "vmware.png"));
    list.add(new SearchEntryTypeBean("avi", "Movie", "movie.png"));
    list.add(new SearchEntryTypeBean("mp4", "Movie", "movie.png"));
    list.add(new SearchEntryTypeBean("flv", "Movie", "movie.png"));
    list.add(new SearchEntryTypeBean("crt", "CRT", "certificate.png"));
    list.add(new SearchEntryTypeBean("zip", "ZIP", "zip.png"));
    list.add(new SearchEntryTypeBean("iso", "ISO", "cd.png"));
    DEFAULT_TYPES = Collections.unmodifiableCollection(list);
  }

  /**
   * This method gets the default {@link SearchEntryType} for {@link SearchEntryType#ID_ANY}.
   * 
   * @return the any type.
   */
  public static SearchEntryType getEntryTypeAny() {

    return ENTRY_TYPE_ANY;
  }

  /**
   * This method gets the {@link Collection} with the default {@link SearchEntryType}s.
   * 
   * @return the default {@link SearchEntryType}s.
   */
  public static Collection<SearchEntryType> getDefaultTypes() {

    return DEFAULT_TYPES;
  }
}
