/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.HashKey;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.CollectionUtil;
import net.sf.mmm.util.reflect.InstantiationFailedException;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathCreationException;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathException;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionUndefinedException;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathRecognizer;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathSegmentIsNullException;

/**
 * This is the abstract base implementation of the {@link PojoPathNavigator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPathNavigator implements PojoPathNavigator {

  /**
   * The constructor.
   */
  public AbstractPojoPathNavigator() {

    super();
  }

  /**
   * This method gets the optional {@link PojoPathFunctionManager} for
   * {@link PojoPathFunction}s that are global for this
   * {@link PojoPathNavigator} instance.
   * 
   * @return the {@link PojoPathFunctionManager} or <code>null</code> if NOT
   *         available.
   */
  protected PojoPathFunctionManager getFunctionManager() {

    return null;
  }

  /**
   * This method gets the {@link CollectionUtil}.
   * 
   * @return the {@link CollectionUtil}.
   */
  protected CollectionUtil getCollectionUtil() {

    return CollectionUtil.getInstance();
  }

  /**
   * This method gets the {@link ReflectionUtil}.
   * 
   * @return the {@link ReflectionUtil}.
   */
  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtil.getInstance();
  }

  /**
   * This method gets the {@link PojoPathCache} from the given
   * <code>context</code>.
   * 
   * @param initialPojo is the initial POJO this {@link PojoPathNavigator} was
   *        invoked with.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the {@link PojoPathCache} or <code>null</code> if caching is
   *         disabled.
   */
  @SuppressWarnings("unchecked")
  protected PojoPathCache getCache(Object initialPojo, PojoPathContext context) {

    Map<Object, Object> rawCache = context.getCache();
    if (rawCache == null) {
      return null;
    }
    HashKey<Object> hashKey = new HashKey<Object>(initialPojo);
    PojoPathMasterCache masterCache = (PojoPathMasterCache) rawCache.get(hashKey);
    if (masterCache == null) {
      masterCache = new PojoPathMasterCache(initialPojo);
      rawCache.put(hashKey, masterCache);
    }
    return masterCache.createAdapter();
  }

  /**
   * {@inheritDoc}
   */
  public Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, InstantiationFailedException {

    if (pojo == null) {
      throw new NlsIllegalArgumentException("Initial POJO can NOT be null!");
    }
    PojoPathCache cache = getCache(pojo, context);
    CachingPojoPath path = getRecursive(pojo, pojoPath, mode, context, cache);
    return path.getPojo();
  }

  /**
   * This method recursively navigates the given <code>pojoPath</code>.
   * 
   * @param initialPojo is the initial
   *        {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to operate on.
   * @param pojoPath is the current
   *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param cache is the {@link #getCache(Object, PojoPathContext) cache} to use
   *        or <code>null</code> to disable caching.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   */
  @SuppressWarnings("unchecked")
  protected CachingPojoPath getRecursive(Object initialPojo, String pojoPath, PojoPathMode mode,
      PojoPathContext context, PojoPathCache cache) {

    CachingPojoPath currentPath = null;
    // try to read from cache...
    if (cache != null) {
      currentPath = cache.get(pojoPath);
      if (currentPath != null) {
        // cached path available - use it as is?
        boolean useCache = context.isCachingUnsafe();
        if (!useCache) {
          // ensure safe caching...
          if (cache.isSafeCachingChecked()) {
            useCache = !currentPath.isInvalidated();
          } else {
            useCache = currentPath.checkForSafeCaching();
            cache.setSafeCachingChecked();
          }
        }
        if (useCache) {
          return currentPath;
        }
      }
    }
    // if we can NOT use the cached result as is, we can still reuse the
    // currentPath object that holds the entire parsed pojoPath.
    if (currentPath == null) {
      currentPath = new CachingPojoPath(pojoPath);
    }
    String parentPathString = currentPath.getParentPath();
    Object parentPojo = initialPojo;
    if (parentPathString != null) {
      // if out pojoPath is more than a segment, we do a recursive invocation
      CachingPojoPath parentPath = getRecursive(initialPojo, parentPathString, mode, context, cache);
      // get the result of the parent-path evaluation
      parentPojo = parentPath.pojo;
      // connect the path
      currentPath.parent = parentPath;
    }

    // handle null value according to mode...
    if (parentPojo == null) {
      switch (mode) {
        case FAIL_IF_NULL:
          throw new PojoPathSegmentIsNullException(initialPojo, pojoPath);
        case RETURN_IF_NULL:
          return null;
        default :
          // this is actually an internal error
          throw new PojoPathSegmentIsNullException(initialPojo, pojoPath);
      }
    }

    // now we start our actual evaluation...
    Object result = evaluate(initialPojo, currentPath, parentPojo, mode, context, cache);
    if (result == null) {
      currentPath.pojo = null;
      currentPath.pojoHashCode = 0;
      // creation has already taken place...
      if (mode != PojoPathMode.RETURN_IF_NULL) {
        throw new PojoPathSegmentIsNullException(initialPojo, pojoPath);
      }
    } else {
      currentPath.pojo = result;
      if (cache != null) {
        if (!context.isCachingUnsafe() && !cache.isDisabled()) {
          currentPath.pojoHashCode = result.hashCode();
        }
        cache.set(pojoPath, currentPath);
      }
      PojoPathRecognizer recognizer = context.getRecognizer();
      if (recognizer != null) {
        recognizer.recognize(result, currentPath);
      }
    }
    return currentPath;
  }

  /**
   * This method evaluates a the single segment of the given
   * <code>currentPath</code> on the <code>parentPojo</code>. It performs a
   * "get" and if the result is <code>null</code> and <code>mode</code> is
   * {@link PojoPathMode#CREATE_IF_NULL} it creates and applies the missing
   * {@link net.sf.mmm.util.reflect.pojo.api.Pojo}.
   * 
   * @param initialPojo is the initial
   *        {@link net.sf.mmm.util.reflect.pojo.api.Pojo}. It is only needed
   *        for exception reporting.
   * @param currentPath is the current {@link CachingPojoPath} to evaluate.
   * @param parentPojo is the parent object to work on.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param cache is the {@link #getCache(Object, PojoPathContext) cache} to use
   *        or <code>null</code> to disable caching.
   * @return the result of the evaluation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   */
  @SuppressWarnings("unchecked")
  protected Object evaluate(Object initialPojo, CachingPojoPath currentPath, Object parentPojo,
      PojoPathMode mode, PojoPathContext context, PojoPathCache cache) {

    String pojoPath = currentPath.getPojoPath();
    Object result;
    String functionName = currentPath.getFunction();
    if (functionName != null) {
      // current segment is a function...
      PojoPathFunction function = getFunction(functionName, context);
      result = function.get(parentPojo, functionName, context);
      if ((result == null) && (mode == PojoPathMode.CREATE_IF_NULL)) {
        result = function.create(parentPojo, functionName, context);
        if (result == null) {
          throw new PojoPathCreationException(initialPojo, pojoPath);
        }
      }
      if ((cache != null) && (!function.isDeterministic())) {
        cache.setDisabled();
      }
    } else {
      // current segment is NOT a function
      if (parentPojo instanceof Map) {
        Map map = (Map) parentPojo;
        // determine pojo type
        Type currentType = null;
        if (currentPath != null) {
          Type pojoType = currentPath.getPojoType();
          if ((pojoType != null) && (pojoType instanceof ParameterizedType)) {
            ParameterizedType type = (ParameterizedType) pojoType;
            Type[] genericArgs = type.getActualTypeArguments();
            if (genericArgs.length == 2) {
              currentType = genericArgs[1];
            }
          }
        }
        if (currentType != null) {
          currentPath.setPojoType(currentType);
        }
        result = map.get(currentPath.getSegment());
        if ((result == null) && (mode == PojoPathMode.CREATE_IF_NULL)) {
          if (currentType == null) {
            throw new PojoPathCreationException(initialPojo, pojoPath);
          }
          Class<?> currentClass = getReflectionUtil().toClass(currentType);
          try {
            result = context.getPojoFactory().newInstance(currentClass);
            if (result == null) {
              throw new PojoPathCreationException(initialPojo, pojoPath);
            }
            map.put(currentPath.getSegment(), result);
          } catch (InstantiationFailedException e) {
            throw new PojoPathCreationException(e, initialPojo, pojoPath);
          }
        }
      } else {
        Integer index = currentPath.getIndex();
        if (index != null) {
          // handle indexed segment for list or array...
          result = getCollectionUtil().get(parentPojo, index.intValue());
          Type pojoType = null;
          if (currentPath != null) {
            pojoType = currentPath.getPojoType();
          }
          if ((pojoType == null) && (result != null)) {
            pojoType = result.getClass();
          }
          Type currentType = null;
          if (pojoType != null) {
            currentType = getReflectionUtil().getComponentType(pojoType, true);
          }
          if (currentType != null) {
            currentPath.setPojoType(currentType);
          }
          if ((result == null) && (mode == PojoPathMode.CREATE_IF_NULL)) {
            if (currentType == null) {
              throw new PojoPathCreationException(initialPojo, pojoPath);
            }
            Class<?> currentClass = getReflectionUtil().toClass(currentType);
            try {
              result = context.getPojoFactory().newInstance(currentClass);
              getCollectionUtil().set(parentPojo, index.intValue(), result);
            } catch (InstantiationFailedException e) {
              throw new PojoPathCreationException(e, initialPojo, pojoPath);
            }
          }
        } else {
          // TODO
          result = getProperty(parentPojo, currentPath.getSegment(), mode);
        }
      }
    }
    return result;
  }

  /**
   * This method gets the {@link PojoPathFunction} for the given
   * <code>functionName</code>.
   * 
   * @param functionName is the
   *        {@link PojoPathFunctionManager#getFunction(String) name} of the
   *        requested {@link PojoPathFunction}.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the requested {@link PojoPathFunction}.
   * @throws PojoPathFunctionUndefinedException if no {@link PojoPathFunction}
   *         is defined for the given <code>functionName</code>.
   */
  @SuppressWarnings("unchecked")
  protected PojoPathFunction getFunction(String functionName, PojoPathContext context)
      throws PojoPathFunctionUndefinedException {

    PojoPathFunction function = null;
    // context overrides functions...
    PojoPathFunctionManager manager = context.getAdditionalFunctionManager();
    if (manager != null) {
      function = manager.getFunction(functionName);
    }
    if (function == null) {
      // global functions as fallback...
      manager = getFunctionManager();
      if (manager != null) {
        function = manager.getFunction(functionName);
      }
    }
    if (function == null) {
      throw new PojoPathFunctionUndefinedException(functionName);
    }
    return function;
  }

  /**
   * This method gets the value of the specified <code>property</code> from
   * the given <code>pojo</code>.
   * 
   * @param pojo is the actual {@link net.sf.mmm.util.reflect.pojo.api.Pojo}
   *        where to get the <code>property</code> from.
   * @param property is the name of the property to get.
   * @param mode is the {@link PojoPathMode} that determines how to deal with
   *        <code>null</code> values.
   * @return the value of the specified <code>property</code>.
   */
  protected abstract Object getProperty(Object pojo, String property, PojoPathMode mode);

  /**
   * This method sets the specified <code>property</code> of the given
   * <code>pojo</code> to the given <code>value</code>.
   * 
   * @param pojo is the actual {@link net.sf.mmm.util.reflect.pojo.api.Pojo}
   *        where to set the <code>property</code>.
   * @param property is the name of the property to set.
   * @param value is the value to set. It may be <code>null</code>.
   */
  protected abstract void setProperty(Object pojo, String property, Object value);

  /**
   * {@inheritDoc}
   */
  public Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context,
      Object value) throws PojoPathException, InstantiationFailedException {

    if (pojo == null) {
      throw new NlsIllegalArgumentException("Initial POJO can NOT be null!");
    }
    CachingPojoPath path = new CachingPojoPath(pojoPath);
    String parent = path.getParentPath();
    Object current = pojo;
    if (parent != null) {
      current = get(pojo, parent, mode, context);
    }
    if (current == null) {
      if (mode == PojoPathMode.RETURN_IF_NULL) {
        return null;
      }
      // this applies for all other modes, because otherwise the segment would
      // have been created.
      throw new PojoPathSegmentIsNullException(pojo, pojoPath);
    }
    setProperty(pojo, path.getSegment(), value);
    return value;
  }

  /**
   * This inner class represents the master-cache for
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}s.
   */
  protected static class PojoPathMasterCache {

    /**
     * The actual cache that maps a
     * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath#getPojoPath() PojoPath}
     * to the resulting {@link CachingPojoPath}.
     */
    private final Map<String, CachingPojoPath> cache;

    /** The initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo}. */
    private final Object initialPojo;

    /**
     * The cached {@link Object#hashCode() hash-code} of the
     * {@link #initialPojo}.
     */
    private int cachedHash;

    /**
     * The constructor.
     * 
     * @param initialPojo is the initial
     *        {@link net.sf.mmm.util.reflect.pojo.api.Pojo} for this cache.
     */
    public PojoPathMasterCache(Object initialPojo) {

      super();
      this.initialPojo = initialPojo;
      this.cachedHash = initialPojo.hashCode();
      this.cache = new HashMap<String, CachingPojoPath>();
    }

    /**
     * This method creates a new {@link PojoPathCache} instance based on this
     * {@link PojoPathMasterCache}.
     * 
     * @return the new {@link PojoPathCache} instance.
     */
    protected PojoPathCache createAdapter() {

      int currentHash = this.initialPojo.hashCode();
      if (currentHash != this.cachedHash) {
        // initial POJO has changed, lets nuke the cached POJOs...
        for (CachingPojoPath path : this.cache.values()) {
          path.invalidate();
        }
        this.cachedHash = currentHash;
      }
      return new PojoPathCache(this.cache);
    }

  }

  /**
   * This inner class represents a cache for
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}s. It is also
   * "misused" to carry state-information about the caching.
   */
  protected static class PojoPathCache {

    /**
     * The actual cache that maps a
     * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath#getPojoPath() PojoPath}
     * to the resulting {@link net.sf.mmm.util.reflect.pojo.api.Pojo}.
     */
    private final Map<String, CachingPojoPath> cache;

    /** @see #setDisabled() */
    private boolean disabled;

    /** @see #isSafeCachingChecked() */
    private boolean safeCachingChecked;

    /**
     * The constructor.
     * 
     * @param cache is the underlying {@link Map} used for caching.
     */
    protected PojoPathCache(Map<String, CachingPojoPath> cache) {

      super();
      this.cache = cache;
    }

    /**
     * This method gets the {@link CachingPojoPath} from this cache.
     * 
     * @param pojoPath is the
     *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} to lookup.
     * @return the cached {@link CachingPojoPath} or <code>null</code> if NOT
     *         (yet) cached.
     */
    public CachingPojoPath get(String pojoPath) {

      return this.cache.get(pojoPath);
    }

    /**
     * This method stored a {@link CachingPojoPath} in this cache. This method
     * will do nothing after it was {@link #isDisabled() disabled}.
     * 
     * @param pojoPath is the
     *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} leading to
     *        the given <code>pojo</code>.
     * @param evaluatedPojoPath is the {@link CachingPojoPath} that has been
     *        evaluated and should be cached.
     */
    public void set(String pojoPath, CachingPojoPath evaluatedPojoPath) {

      if (!this.disabled) {
        this.cache.put(pojoPath, evaluatedPojoPath);
      }
    }

    /**
     * This method determines if this cache has been
     * {@link #setDisabled() disabled}.
     * 
     * @return <code>true</code> if this cache is disabled, <code>false</code>
     *         otherwise.
     */
    public boolean isDisabled() {

      return this.disabled;
    }

    /**
     * This method disables further
     * {@link #set(String, CachingPojoPath) caching}.
     */
    public void setDisabled() {

      this.disabled = true;
    }

    /**
     * This method determines if the first cached {@link CachingPojoPath} has
     * been
     * {@link CachingPojoPath#checkForSafeCaching() checked for safe-caching}.
     * 
     * @return the safeCachingChecked
     */
    public boolean isSafeCachingChecked() {

      return this.safeCachingChecked;
    }

    /**
     * This method sets the {@link #isSafeCachingChecked() safe-caching-checked}
     * flag.
     */
    public void setSafeCachingChecked() {

      this.safeCachingChecked = true;
    }

  }

  /**
   * This class represents a
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}. It contains the
   * internal logic to validate and parse the
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}. Additional it can
   * also hold the {@link #getPojo() result} of the evaluation and the
   * {@link #getPojoType() generic type}.
   * 
   * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
   */
  protected static class CachingPojoPath extends BasicPojoPath {

    /** @see #getParent() */
    private CachingPojoPath parent;

    /** @see #getPojoType() */
    private Type pojoType;

    /** @see #getPojo() */
    private Object pojo;

    /** @see #getPojoHashCode() */
    private int pojoHashCode;

    /**
     * The constructor.
     * 
     * @param pojoPath is the {@link #getPojoPath() path} to represent.
     */
    public CachingPojoPath(String pojoPath) {

      super(pojoPath);
      // initially invalidated
      this.pojoHashCode = -1;
    }

    /**
     * @return the parent
     */
    public CachingPojoPath getParent() {

      return this.parent;
    }

    /**
     * @param parent is the parent to set
     */
    public void setParent(CachingPojoPath parent) {

      this.parent = parent;
    }

    /**
     * This method gets the {@link Type type} of the {@link #getPojo() Pojo}
     * this {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} is leading
     * to.
     * 
     * @return the property-type or <code>null</code> if NOT set.
     */
    public Type getPojoType() {

      return this.pojoType;
    }

    /**
     * This method sets the {@link #getPojoType() pojo-type}.
     * 
     * @param propertyType is the property-type to set.
     */
    public void setPojoType(Type propertyType) {

      this.pojoType = propertyType;
    }

    /**
     * This method gets the {@link net.sf.mmm.util.reflect.pojo.api.Pojo}
     * instance this {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} is
     * leading to.
     * 
     * @return the {@link net.sf.mmm.util.reflect.pojo.api.Pojo} or
     *         <code>null</code>.
     */
    public Object getPojo() {

      return this.pojo;
    }

    /**
     * This method sets the {@link #getPojo() pojo-instance}.
     * 
     * @param pojo is the {@link #getPojo() pojo-instance}.
     */
    public void setPojo(Object pojo) {

      this.pojo = pojo;
    }

    /**
     * @return the pojoHashCode
     */
    public int getPojoHashCode() {

      return this.pojoHashCode;
    }

    /**
     * @param pojoHashCode is the pojoHashCode to set
     */
    public void setPojoHashCode(int pojoHashCode) {

      this.pojoHashCode = pojoHashCode;
    }

    /**
     * This method invalidates the {@link #getPojo() cached result} for this
     * path.
     */
    protected void invalidate() {

      // nuke cached pojo...
      this.pojo = null;
      this.pojoHashCode = -1;
    }

    /**
     * This method determines if this path has been
     * {@link #invalidate() invalidated}.
     * 
     * @return <code>true</code> if invalidated, <code>false</code>
     *         otherwise.
     */
    protected boolean isInvalidated() {

      if ((this.pojo == null) && (this.pojoHashCode != 0)) {
        return true;
      }
      return false;
    }

    /**
     * This method checks if the this
     * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} is safe to be used
     * as cached result.
     * 
     * @return <code>true</code> if cached result is safe, <code>false</code>
     *         otherwise.
     */
    protected boolean checkForSafeCaching() {

      boolean safe = false;
      if (this.pojo == null) {
        if (this.pojoHashCode == 0) {
          safe = true;
        }
      } else {
        int hash = this.pojo.hashCode();
        if (this.pojoHashCode == hash) {
          safe = true;
        }
      }
      if (this.parent != null) {
        boolean parentSafe = this.parent.checkForSafeCaching();
        if (!parentSafe) {
          safe = false;
        }
      }
      if (!safe) {
        invalidate();
      }
      return safe;
    }

  }

}
