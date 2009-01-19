/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides an API and base-implementation for a persistence-layer.
 * <h2>Persistence Utilities</h2>
 * After the pain with EJB 1 and 2, Sun published the JPA (Java Persistence API).
 * Why do we need yet another persistence-API?<br>
 * <br>
 * The JPA acts as API and abstraction to object/relation-mappers such as 
 * hibernate. However most projects create a large layer on top of this 
 * containing custom {@link net.sf.mmm.util.persistence.api.PersistenceEntity entities} 
 * and {@link net.sf.mmm.util.persistence.api.PersistenceEntityManager DAO}s.<br>
 * The aim of this project is to provide an API for this upper layer of the 
 * persistence. It shall help you with the design and offer you individual as 
 * well as generic access to your persistence.<br>
 * This API makes some little assumptions that are quite common (see 
 * {@link net.sf.mmm.util.persistence.api.PersistenceEntity}). If the API 
 * shall NOT fit your needs, you may NOT use this project. However you can get
 * inspired when you design your own API.<br>
 * <br>
 */
package net.sf.mmm.util.persistence;

