/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for searches (queries) as part of the persistence layer.
 * <a name="documentation"/><h2>Persistence Search API</h2> 
 * This package contains the API advanced searching via the persistence layer.<br/>
 * For advanced searches a {@link net.sf.mmm.persistence.api.PersistenceEntityManager DAO}
 * shall provide uniform support. This API suggests that the DAO offers a specific
 * implementation of {@link net.sf.mmm.persistence.api.search.PersistenceSearcher} 
 * for each such search (typically as inner class of the DAO and available via
 * some getter method like <code>getSearcherByAddressData()</code>) as 
 * well as an according implementation of 
 * {@link net.sf.mmm.persistence.api.search.PersistenceSearchQuery} as simple 
 * java bean. This will help you with typical problems such as 
 * {@link net.sf.mmm.persistence.api.search.PersistenceSearchQuery#getMaximumHitCount() 
 * limiting the number of hits}, 
 * {@link net.sf.mmm.persistence.api.search.PersistenceSearchQuery#getSearchTimeout() 
 * setting timeouts}, {@link net.sf.mmm.persistence.api.search.PersistenceSearchResult#isComplete()
 * determining if there are more hits available} as well as reusing the same 
 * query for {@link net.sf.mmm.persistence.api.search.PersistenceSearcher#count(PersistenceSearchQuery) 
 * counting} the total number of hits.
 */
package net.sf.mmm.persistence.api.search;

