/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * This is a mock implementation of {@link TypedQuery}.
 *
 * @param <X> is the generic type of the result objects of this query.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TypedQueryMock<X> implements TypedQuery<X> {

  private  final String query;

  private  final Class<X> resultType;

  private  final List<Object> parameterList;

  private  final Map<String, Object> parameterMap;

  /**
   * The constructor.
   *
   * @param query - see {@link #getQuery()}.
   * @param resultType - see {@link #getResultType()}.
   */
  public TypedQueryMock(String query, Class<X> resultType) {

    super();
    this.query = query;
    this.resultType = resultType;
    this.parameterList = new LinkedList<>();
    this.parameterMap = new HashMap<>();
  }

  /**
   * @return the query
   */
  public String getQuery() {

    return this.query;
  }

  /**
   * @return the resultType
   */
  public Class<X> getResultType() {

    return this.resultType;
  }

  @Override
  public int executeUpdate() {

    // mock
    return 0;
  }

  @Override
  public int getFirstResult() {

    // mock
    return 0;
  }

  @Override
  public FlushModeType getFlushMode() {

    // mock
    return null;
  }

  @Override
  public Map<String, Object> getHints() {

    // mock
    return null;
  }

  @Override
  public LockModeType getLockMode() {

    // mock
    return null;
  }

  @Override
  public int getMaxResults() {

    // mock
    return 0;
  }

  @Override
  public Parameter<?> getParameter(String arg0) {

    // mock
    return null;
  }

  @Override
  public Parameter<?> getParameter(int arg0) {

    // mock
    return null;
  }

  @Override
  public <T> Parameter<T> getParameter(String arg0, Class<T> arg1) {

    // mock
    return null;
  }

  @Override
  public <T> Parameter<T> getParameter(int arg0, Class<T> arg1) {

    // mock
    return null;
  }

  @Override
  public <T> T getParameterValue(Parameter<T> arg0) {

    // mock
    return null;
  }

  @Override
  public Object getParameterValue(String name) {

    // mock
    return this.parameterMap.get(name);
  }

  @Override
  public Object getParameterValue(int index) {

    // mock
    return this.parameterList.get(index);
  }

  @Override
  public Set<Parameter<?>> getParameters() {

    // mock
    return null;
  }

  @Override
  public boolean isBound(Parameter<?> arg0) {

    // mock
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> arg0) {

    // mock
    return null;
  }

  @Override
  public List<X> getResultList() {

    // mock
    return null;
  }

  @Override
  public X getSingleResult() {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setFirstResult(int arg0) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setFlushMode(FlushModeType arg0) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setHint(String arg0, Object arg1) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setLockMode(LockModeType arg0) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setMaxResults(int arg0) {

    // mock
    return null;
  }

  @Override
  public <T> TypedQuery<X> setParameter(Parameter<T> arg0, T arg1) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(String name, Object parameter) {

    // mock
    this.parameterMap.put(name, parameter);
    return this;
  }

  @Override
  public TypedQuery<X> setParameter(int index, Object parameter) {

    // mock
    while (index >= this.parameterList.size()) {
      this.parameterList.add(null);
    }
    this.parameterList.set(index, parameter);
    return this;
  }

  @Override
  public TypedQuery<X> setParameter(Parameter<Calendar> arg0, Calendar arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(Parameter<Date> arg0, Date arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(String arg0, Calendar arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(String arg0, Date arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(int arg0, Calendar arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public TypedQuery<X> setParameter(int arg0, Date arg1, TemporalType arg2) {

    // mock
    return null;
  }

  @Override
  public String toString() {

    return this.query;
  }

}
