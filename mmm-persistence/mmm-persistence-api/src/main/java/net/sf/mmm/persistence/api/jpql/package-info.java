/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for the Java Persistence Query Language (JPQL).
 * <a name="documentation"/><h2>Persistence API JPQL</h2>
 * This package contains the specification of the {@link net.sf.mmm.persistence.api.jpql.JpqlSyntax Java Persistence
 * Query Language (JPQL)} syntax as JavaDoc.
 * Initially we created our own type-safe builder for dynamic JPQL statements (mmm-persistence-query). However, we discovered
 * that <a href="http://www.querydsl.com/">QueryDSL</a> already provides this in a ideal way as ASL 2.0 licensed OSS
 * project so we dropped our own API and implementation and recommend to use Query-DSL (rather than
 * {@link javax.persistence.criteria.CriteriaBuilder Criteria API}).
 */
package net.sf.mmm.persistence.api.jpql;

