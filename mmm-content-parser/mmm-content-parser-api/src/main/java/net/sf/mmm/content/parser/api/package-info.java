/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for parsers to extract metadata from content.
 * <a name="documentation"></a><h2>Content-Parser API</h2>
 * This package contains the API and base-implementation for a
 * {@link net.sf.mmm.content.parser.api.ContentParser} that is used to extract 
 * metadata from data of a specific content-type. Further the
 * {@link net.sf.mmm.content.parser.api.ContentParserService} gives access to
 * all available {@link net.sf.mmm.content.parser.api.ContentParser}s by their 
 * content-type (extension or mimetype). <br>
 * The {@link net.sf.mmm.content.parser.api.ContentParser}-implementations should
 * respect the value of
 * {@link net.sf.mmm.content.parser.api.ContentParserOptions#getMaximumBufferSize()} 
 * so the memory footprint can be limited. This is an important feature if you 
 * want to parse large sets of content without getting an 
 * {@link java.lang.OutOfMemoryError}.
 */
package net.sf.mmm.content.parser.api;

