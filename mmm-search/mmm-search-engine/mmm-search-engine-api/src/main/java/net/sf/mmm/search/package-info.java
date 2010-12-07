/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides full-text search that can be easily integrated into any application.
 * <a name="documentation"/><h2>mmm-search</h2>
 * Here you will find how to use <code>mmm-search</code>.
 * <h3>Configuration</h3>
 * First of all you have to create an XML configuration file
 * under <code>~/.mmmm/search.xml</code> (in windows ~ is %USERPROFILE% that is 
 * something like "C:\Documents and Settings\mylogin" or 
 * "C:\Windows\Profiles\mylogin" - If you cannot create a folder named ".mmm" 
 * in windows explorer, use MSDos and call "md .mmm" ). Here is an example for 
 * your configuration file:
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <search>
 *   <!-- location of your search index on disc -->
 *   <search-index location="file://~/.mmm/search-index/"/>
 *   <!-- sources to index and for filtering searches in UI -->
 *   <sources>
 *     <!-- virtual source for no filter in UI -->
 *     <source id="" title="Any"/>
 *     <source id="wiki" title="Wiki" url-prefix="http://foo.org/wiki/">
 *       <locations>
 *         <!-- locations to index (in this example text and attachments of foswiki) -->
 *         <location absolute-uris="false" encoding="UTF-8" filter="wiki-filter" location-uri="file:///srv/wiki/data/" uri-transformer="wiki-transformer"/>
 *         <location absolute-uris="false" encoding="UTF-8" filter="wiki-filter" location-uri="file:///srv/wiki/pub/"/>
 *       </locations>
 *     </source>
 *     <source id="svn" title="Subversion" url-prefix="http://foo.org/subversion/repository/trunk/">
 *       <locations>
 *         <location absolute-uris="true" filter="svn-filter" location-uri="file:///data/svn/trunk"/>
 *       </locations>
 *     </source>
 *   </sources>
 *   <!-- Filters referenced by locations to index. Only folders and files that 
 *        are matched by this filters will be crawled and indexed -->
 *   <filters>
 *     <filter-chain default-result="true" id="default-filter">
 *       <rule include="false" pattern="/\.svn$"/>
 *       <rule include="false" pattern="/\.git$"/>
 *       <rule include="false" pattern="/\.bzr$"/>
 *       <rule include="false" pattern="/\.hg$"/>
 *       <rule include="false" pattern="/CSV$"/>
 *     </filter-chain>
 *     <filter-chain id="svn-filter" default-result="true" parent="default-filter">
 *       <!-- ignore mavens target folder -->
 *       <rule include="false" pattern="/target$"/>
 *       <rule include="false" pattern="/eclipse-target$"/>
 *       <rule include="false" pattern="/\.settings$"/>
 *     </filter-chain>
 *     <filter-chain id="wiki-filter" default-result="true" parent="default-filter">
 *       <!-- ignore folders (and files) that start with '_' (e.g. _empty) -->
 *       <rule include="false" pattern="/_.*$"/>
 *       <!-- ignore Trash and Sandbox -->
 *       <rule include="false" pattern="/(Trash|Sandbox)$"/>
 *       <!-- ignore twiki history files -->
 *       <rule include="false" pattern=",v$"/>
 *       <!-- ignore twiki lease files -->
 *       <rule include="false" pattern=".lease$"/>
 *       <!-- ignore log files -->
 *       <rule include="false" pattern="log.*\.txt$"/>
 *     </filter-chain>
 *   </filters>
 *   <!-- In the fields section you can define the schema of your search index.
 *        mmm-search defines a set of internal fields (see SearchEntry) that
 *        should be sufficient for standard use-cases.
 *        Internal fields are inherited by default but can also be overridden 
 *        (see text+cid). Additional custom fields have to be defined here. -->
 *   <!--
 *   <fields>
 *     <field name="cid" type="long" mode="searchable-and-retrievable"/>
 *     <field name="date" type="date" mode="searchable-and-retrievable"/>
 *     <field name="text" type="string" mode="searchable"/>
 *   </fields>
 *   -->
 *   <!-- URI rewriters can be referenced by locations to index.
 *        In this case the URI ([relative] path) of the resource to index gets
 *        transformed before it is written to the index. The example shows how 
 *        to use this for foswiki that has topics as *.txt files on the disc but
 *        in the URLs the ".txt" extension must be omitted. -->
 *   <uri-rewriters>
 *     <transformer-chain id="wiki-transformer">
 *       <regex pattern=".txt$" replace-all="false" replacement="" stop-on-match="false"/>
 *     </transformer-chain>
 *   </uri-rewriters>
 *   <!-- Entry-types for UI (filtering and icons for hits). -->
 *   <entry-types extend-defaults="true">
 *     <!-- Here you can add additional entry-types (file-types). If two types 
 *          share the same name, they are combined. In this case only the major
 *          type should define an icon. -->
 *     <!--
 *     <type id="" title="Any" icon="file.png"/>
 *     <type id="pdf" title="PDF" icon="pdf.png"/>
 *     <type id="doc" title="Word" icon="ms-word.png"/>
 *     <type id="txt" title="Text" icon="text.png"/>
 *     <type id="xml" title="XML" icon="xml.png"/>
 *     <type id="html" title="HTML" icon="firefox.png"/>
 *     -->
 *   </entry-types>
 *   <!-- This is a generic section of properties for (implementation) specific 
 *        features. If the implementation does not know about the property, it 
 *        will simply be ignored. -->
 *   <properties>
 *     <!-- Activate this if you are using the search-engine without the 
 *          provided webapp and you want it to be refreshed periodically. -->
 *     <!--
 *     <property key="auto-refresh">true</property>
 *     -->
 *     <!-- Activate this if you are using lucene and want to disable leading 
 *          wildcards for performance reasons. -->
 *     <!--
 *     <property key="net.sf.mmm.search.engine.impl.lucene.LuceneSearchQueryBuilder.ignoreLeadingWildcards">true</property>
 *     -->
 *   </properties>
 * </search>
 * }
 * </pre>
 * <h3>Indexing</h3>
 * Now that you have created the configuration, you can start the indexing:
 * <pre>
 * java -classpath lib/*.jar net.sf.mmm.search.indexer.impl.SearchIndexerMain
 * </pre>
 * To get the according options invoke with <code>--help</code>. To get the
 * required dependencies get <code>mmm-search-release</code> (as download
 * from sourceforge).
 * The indexer acts incremental by default. You can invoke it as cronjob or 
 * something like this.
 * <h3>Searching</h3>
 * Now that you have a search index, you can start searching.
 * Get the WAR file either from the <code>mmm-search-release</code> (see above) 
 * or from the maven repository at
 * <a href="http://repo1.maven.org/maven2/net/sf/m-m-m/mmm-search-engine-webapp/">mmm-search-engine-webapp</a>
 * and copy it as <code>mmm-search-engine-webapp.war</code> into the 
 * <code>webapps</code> directory of a servlet container (e.g. 
 * <a href="http://tomcat.apache.org">tomcat</a>). After starting your servlet 
 * container, you can start searching with your browser. Visit this URL: 
 * <a href="http://localhost:8080/mmm-search-engine-webapp/search">http://localhost:8080/mmm-search-engine-webapp/search</a>.
 * If your servlet container is running on a different port or host, you have to 
 * adjust the URL accordingly.<br/>
 * <br/>
 * Have fun!
 */
package net.sf.mmm.search;

