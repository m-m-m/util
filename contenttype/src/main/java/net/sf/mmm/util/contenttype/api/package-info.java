/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to detect the type of content on the
 * fly while streaming data.
 * <a name="documentation"></a><h2>Content-Type API</h2>
 * A common problem when dealing with arbitrary content is to detect the type of
 * data and potentially additional metadata. This already starts with 
 * identifying a particular content type. Beside file extensions the most
 * established approach is the <em>mimetype</em>. This API offers the interface 
 * {@link net.sf.mmm.util.contenttype.api.ContentType} for this purpose that
 * identifies a content type as a node of a hierarchical tree. It also contains
 * the mimetype and file extensions. <br>
 * Often applications guess the type of content based on the extension of the
 * filename. However, this causes imprecise or even wrong results and is 
 * sometimes NOT applicable because no filename is present. Another approach is
 * to read the data and seek for characteristic patterns. This approach is done
 * by the GNU linux program {@code file}. <br>
 * This utility also detects the
 * {@link net.sf.mmm.util.contenttype.api.ContentType} of arbitrary data by
 * pattern based detection. However, it allows detection on the fly while
 * streaming data. Further it can also extract additional metadata from the data
 * (e.g. creator, quality information, or whatever is available for the actual
 * content type). Finally, it is even possible to provide predefined metadata
 * that is updated on the fly when the data is streamed (e.g. the artist can be
 * specified and is added or updated while the stream is written to an output
 * stream). This is implemented on the basis of the 
 * {@link net.sf.mmm.util.io.api.DetectorStream} framework.
 */
package net.sf.mmm.util.contenttype.api;

