/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
   * This method gets the segment-cache from the given <code>context</code>.
   * 
   * @param initialPojo is the initial POJO this {@link PojoPathNavigator} was
   *        invoked with.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the segment-cache or <code>null</code> if caching is disabled.
   */
  @SuppressWarnings("unchecked")
  protected SegmentCache getSegmentCache(Object initialPojo, PojoPathContext context) {

    Map<Object, Object> masterCache = context.getCache();
    Map<String, SimplePojoPath> segmentCache = null;
    if (masterCache != null) {
      segmentCache = (Map<String, SimplePojoPath>) masterCache.get(initialPojo);
      if (segmentCache == null) {
        segmentCache = new HashMap<String, SimplePojoPath>();
        masterCache.put(initialPojo, segmentCache);
      }
    }
    if (segmentCache == null) {
      return null;
    } else {
      return new SegmentCache(segmentCache);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, InstantiationFailedException {

    if (pojo == null) {
      throw new NlsIllegalArgumentException("Initial POJO can NOT be null!");
    }
    SegmentCache segmentCache = getSegmentCache(pojo, context);
    return getRecursive(pojo, pojoPath, mode, context, segmentCache);
  }

  /**
   * This method recursively navigates the given <code>pojoPath</code>.
   * 
   * @param pojo is the initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to
   *        operate on.
   * @param pojoPath is the current
   *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param segmentCache if the
   *        {@link #getSegmentCache(Object, PojoPathContext) segment-cache} to
   *        use or <code>null</code> to disable caching.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   */
  @SuppressWarnings("unchecked")
  protected SimplePojoPath getRecursive(Object pojo, String pojoPath, PojoPathMode mode,
      PojoPathContext context, SegmentCache segmentCache) {

    SimplePojoPath parentPath = null;
    if (segmentCache != null) {
      parentPath = segmentCache.get(pojoPath);
      if (parentPath != null) {
        return parentPath;
      }
    }
    SimplePojoPath currentPath = new SimplePojoPath(pojoPath);
    String parentPathString = currentPath.getParentPath();
    Object current = pojo;
    if (parentPathString != null) {
      parentPath = getRecursive(pojo, parentPathString, mode, context, segmentCache);
      current = parentPath.getPojo();
    }
    if (current == null) {
      switch (mode) {
        case FAIL_IF_NULL:
          throw new PojoPathSegmentIsNullException(pojoPath, pojo, parentPathString);
        case RETURN_IF_NULL:
          return null;
        default :
          // this is actually an internal error
          throw new PojoPathSegmentIsNullException(pojoPath, pojo, parentPathString);
      }
    } else {
      String functionName = currentPath.getFunction();
      Object result;
      if (functionName != null) {
        PojoPathFunction function = getFunction(functionName, context);
        result = function.get(current, functionName, context);
        if ((result == null) && (mode == PojoPathMode.CREATE_IF_NULL)) {
          result = function.create(current, functionName, context);
        }
        if ((segmentCache != null) && (!function.isDeterministic())) {
          segmentCache.setDisabled();
        }
      } else {
        if (current instanceof Map) {
          Map map = (Map) current;
          // determine pojo type
          Type currentType = null;
          if (parentPath != null) {
            Type pojoType = parentPath.getPojoType();
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
              throw new PojoPathCreationException(pojo, pojoPath);
            }
            Class<?> currentClass = getReflectionUtil().toClass(currentType);
            try {
              result = context.getPojoFactory().newInstance(currentClass);
              map.put(currentPath.getSegment(), result);
            } catch (InstantiationFailedException e) {
              throw new PojoPathCreationException(pojo, pojoPath, e);
            }
          }
        } else {
          Integer index = currentPath.getIndex();
          if (index != null) {
            result = getCollectionUtil().get(current, index.intValue());
            Type pojoType = null;
            if (parentPath != null) {
              pojoType = parentPath.getPojoType();
            }
            if ((pojoType == null) && (result != null)) {
              pojoType = result.getClass();
            }
            Type currentType = getReflectionUtil().getComponentType(pojoType, true);
            if (currentType != null) {
              currentPath.setPojoType(currentType);
            }
            if ((result == null) && (mode == PojoPathMode.CREATE_IF_NULL)) {
              if (currentType == null) {
                throw new PojoPathCreationException(pojo, pojoPath);
              }
              Class<?> currentClass = getReflectionUtil().toClass(currentType);
              try {
                result = context.getPojoFactory().newInstance(currentClass);
                getCollectionUtil().set(current, index.intValue(), result);
              } catch (InstantiationFailedException e) {
                throw new PojoPathCreationException(pojo, pojoPath, e);
              }
            }
          } else {
            // TODO
            result = getProperty(current, currentPath.getSegment(), mode);
          }
        }
      }
      if (result == null) {
        // creation has already taken place...
        if (mode != PojoPathMode.RETURN_IF_NULL) {
          throw new PojoPathSegmentIsNullException(pojoPath, pojo, parentPathString);
        }
      } else {
        currentPath.setPojo(result);
        if (segmentCache != null) {
          segmentCache.set(pojoPath, currentPath);
        }
        PojoPathRecognizer recognizer = context.getRecognizer();
        if (recognizer != null) {
          recognizer.recognize(result, currentPath);
        }
      }
      return currentPath;
    }
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
   * @param pojo is the actual {@link net.sf.mmm.util.reflect.pojo.api.Pojo} where
   *        to get the <code>property</code> from.
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
   * @param pojo is the actual {@link net.sf.mmm.util.reflect.pojo.api.Pojo} where
   *        to set the <code>property</code>.
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
    SimplePojoPath path = new SimplePojoPath(pojoPath);
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
      throw new PojoPathSegmentIsNullException(pojoPath, pojo, parent);
    }
    setProperty(pojo, path.getSegment(), value);
    return value;
  }

  /**
   * This inner class represents a Cache that maps a
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath#getPojoPath() PojoPath}
   * to the resulting {@link net.sf.mmm.util.reflect.pojo.api.Pojo}.
   */
  protected static class SegmentCache {

    /**
     * The actual cache that maps a
     * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath#getPojoPath() PojoPath}
     * to the resulting {@link net.sf.mmm.util.reflect.pojo.api.Pojo}.
     */
    private final Map<String, SimplePojoPath> cache;

    /** @see #setDisabled() */
    private boolean disabled;

    /**
     * The constructor.
     * 
     * @param cache is the underlying {@link Map} used for caching.
     */
    public SegmentCache(Map<String, SimplePojoPath> cache) {

      super();
      this.cache = cache;
    }

    /**
     * This method gets the {@link SimplePojoPath} from this cache.
     * 
     * @param pojoPath is the
     *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} to lookup.
     * @return the cached {@link SimplePojoPath} or <code>null</code> if NOT
     *         (yet) cached.
     */
    public SimplePojoPath get(String pojoPath) {

      return this.cache.get(pojoPath);
    }

    /**
     * This method stored a {@link SimplePojoPath} in this cache. This method
     * will do nothing after it was {@link #isDisabled() disabled}.
     * 
     * @param pojoPath is the
     *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} leading to
     *        the given <code>pojo</code>.
     * @param evaluatedPojoPath is the {@link SimplePojoPath} that has been
     *        evaluated and should be cached.
     */
    public void set(String pojoPath, SimplePojoPath evaluatedPojoPath) {

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
     * This method disables further {@link #set(String, SimplePojoPath) caching}.
     */
    public void setDisabled() {

      this.disabled = true;
    }

  }

}
