/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.ComposedException;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This class is a context for {@link InternalValidatingIdResolver}. JAXB is a little strange: It is using a
 * {@link Callable} for resolving {@link javax.xml.bind.annotation.XmlIDREF} but {@link Callable#call()
 * evaluates} it immediately instead of storing it until the initial processing phase has been completed. If
 * it returns <code>null</code> it will try again later but never complains if the ID could not be resolved in
 * the end.<br/>
 * This class solves the problem by tracking each {@link Callable} and check that in the
 * {@link #disposeAndValidate() end} all are resolved successfully.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class IdResolverContext {

  /** The bundle for creating error messages. */
  private final NlsBundleUtilCore bundle;

  /** @see #put(String, Object) */
  private Map<String, Object> id2valueMap;

  /** @see #get(String, Class) */
  private Map<String, Resolver> id2callableMap;

  /** @see #put(String, Object) */
  private List<NlsObject> duplicateIdErrors;

  /**
   * The constructor.
   */
  public IdResolverContext() {

    super();
    this.id2valueMap = new HashMap<String, Object>();
    this.id2callableMap = new HashMap<String, IdResolverContext.Resolver>();
    this.duplicateIdErrors = new ArrayList<NlsObject>();
    this.bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCore.class);
  }

  /**
   * This method {@link com.sun.xml.bind.IDResolver#bind(String, Object) binds} the given <code>value</code>
   * with the given <code>id</code>.
   * 
   * @param id is the {@link javax.xml.bind.annotation.XmlID}.
   * @param value is the JAXB bean to bind.
   */
  public void put(String id, Object value) {

    Object old = this.id2valueMap.put(id, value);
    if ((old != null) && (old != value)) {
      this.duplicateIdErrors.add(this.bundle.errorDuplicateObjectWithKey(value, id));
    }
  }

  /**
   * This method {@link com.sun.xml.bind.IDResolver#resolve(String, Class) resolves} the value with the given
   * <code>id</code>.
   * 
   * @param id is the {@link javax.xml.bind.annotation.XmlIDREF} of the requested object. This object should
   *        be {@link #put(String, Object) bound}. This may also happen after it has been requested.
   * @param type is the expected type of the object.
   * @return a {@link Callable} that {@link Callable#call() resolves} the requested value.
   */
  public Callable<?> get(String id, Class<?> type) {

    Resolver callable = this.id2callableMap.get(id);
    if (callable == null) {
      callable = new Resolver(id, type);
      this.id2callableMap.put(id, callable);
    }
    return callable;
  }

  /**
   * This method disposes this context and performs a validation that all IDs have been resolved.
   * 
   * @throws ObjectNotFoundException if a single ID was NOT resolved.
   * @throws ComposedException if multiple IDs have NOT been resolved.
   */
  public void disposeAndValidate() throws ObjectNotFoundException, ComposedException {

    List<NlsObject> errorList = this.duplicateIdErrors;
    this.duplicateIdErrors = null;
    for (Resolver resolver : this.id2callableMap.values()) {
      if (!resolver.resolved) {
        errorList.add(this.bundle.errorObjectNotFound(resolver.type, resolver.id));
      }
    }
    this.id2valueMap = null;
    this.id2callableMap = null;
    int errorCount = errorList.size();
    if (errorCount > 0) {
      NlsObject[] errors = errorList.toArray(new NlsObject[errorCount]);
      throw new ComposedException(errors);
    }
  }

  /**
   * This inner class resolves an ID on demand.
   */
  protected class Resolver implements Callable<Object> {

    /** @see #call() */
    private final String id;

    /** @see #call() */
    private final Class<?> type;

    /** @see #isResolved() */
    private boolean resolved;

    /**
     * The constructor.
     * 
     * @param id is the ID to resolve.
     * @param type is the expected type of the object to resolve.
     */
    public Resolver(String id, Class<?> type) {

      super();
      this.id = id;
      this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public Object call() throws Exception {

      Object result = IdResolverContext.this.id2valueMap.get(this.id);
      this.resolved = (result != null);
      return result;
    }

    /**
     * This method determines if this {@link Resolver} has been {@link #call() resolved} successfully.
     * 
     * @return <code>true</code> if resolved successfully, <code>false</code> otherwise.
     */
    public boolean isResolved() {

      return this.resolved;
    }
  }

}
