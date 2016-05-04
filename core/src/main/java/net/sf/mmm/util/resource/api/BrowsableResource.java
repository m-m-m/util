/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a {@link DataResource} that has higher-level features and may contain other resources. You
 * can think of a {@link BrowsableResource} as a {@link java.io.File file} that is a {@link java.io.File#isDirectory()
 * directory} or a {@link java.io.File#isFile() regular file}. However it may be both and it can originate from other
 * sources than the filesystem. It was created before {@link java.nio.file.Path} was available and a migration was
 * started but failed due to design restrictions of NIO. However, this API is more lightweight than
 * {@link java.nio.file.Path} and especially {@link java.nio.file.FileSystem} that also prevents some scenarios such as
 * creating a sub-filesystem where the root is some sub-folder of the main filesystem.<br>
 * <b>ATTENTION:</b><br>
 * This API has a high level of abstraction. It is possible that the underlying implementation e.g. forms a web-crawler
 * where a HTML-page is {@link #isData() data} containing the HTML-content as well as a {@link #isFolder() folder}
 * containing the linked sites. Further you have to be careful when recursively scanning {@link BrowsableResource}s that
 * you avoid infinity loops. E.g. create a {@link java.util.Set} holding the {@link #getUri() URIs} of the
 * {@link BrowsableResource}s that have already been visited in case you are completely abstracting from the source of
 * the data.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface BrowsableResource extends DataResource {

  /**
   * This method iterates the immediate child-{@link BrowsableResource resources} contained in this
   * {@link BrowsableResource}. <br>
   * If this is no {@link #isFolder() folder}, this method will always return an empty {@link Iterable}.
   *
   * @return an {@link Iterable} of the child-{@link BrowsableResource resources}.
   */
  Iterable<? extends BrowsableResource> getChildResources();

  /**
   * This method iterates the immediate child-{@link BrowsableResource resources} contained in this
   * {@link BrowsableResource} and are {@link Filter#accept(Object) accepted} by the given {@code filter} . <br>
   *
   * @param filter is the {@link Filter} applied to the {@link #getChildResources() child-resources}.
   * @return an {@link Iterable} of the child-{@link BrowsableResource resources}.
   */
  Iterable<? extends BrowsableResource> getChildResources(Filter<? super BrowsableResource> filter);

  /**
   * This method determines if this {@link BrowsableResource} is a <em>folder</em> that potentially contains other
   * {@link BrowsableResource resources}. Otherwise if this is no folder, {@link #getChildResources()} will be empty
   * (return an empty {@link Iterable}). However {@link #getChildResources()} can also be empty, if this is a folder.
   * <br>
   * In order to determine if this {@link BrowsableResource} is {@link #openStream() containing data}, please use
   * {@link #isAvailable()}. Please note that {@link #isFolder()} and {@link #isAvailable()} can both return
   * {@code true} or both return {@code false}.
   *
   * @return {@code true} if this is a folder, {@code false} otherwise.
   */
  boolean isFolder();

}
