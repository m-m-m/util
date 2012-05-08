/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

/**
 * This is the call-back interface for a {@link PojoPathFunction} that allows to add custom functionality to a
 * {@link PojoPathNavigator}.<br>
 * This can help for various use-cases such as retrieving objects from a database (an O/R-mapper), adding
 * custom logic for calculated or combined attributes, etc.<br>
 * A {@link PojoPathFunction} is {@link PojoPathFunctionManager#getFunction(String) registered} in a
 * {@link PojoPathFunctionManager} under a specific name ( <code>functionName</code>). The
 * {@link PojoPathFunction} itself does NOT contain that name and gets this name back as parameter when it is
 * invoked. Therefore the same {@link PojoPathFunction} instance can be
 * {@link PojoPathFunctionManager#getFunction(String) registered} with different names and can behave
 * different according to the name it was invoked for.<br>
 * 
 * @param <IN> is the generic {@link #getInputClass() input-type}.
 * @param <VALUE> is the generic {@link #getValueClass() value-type}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPathFunction<IN, VALUE> {

  /**
   * This is the prefix used to indicate a {@link PojoPathFunction} in a {@link PojoPath}. The value ({@value}
   * ) will never change. It is NOT necessary to use this constant to construct a {@link PojoPath}.<br>
   * For example the {@link PojoPath#getSegment() segment} <code>&#64;myFunction</code> as part of a
   * {@link PojoPath} such as <code>foo.bar.&#64;myFunction</code> indicates that the {@link PojoPathFunction}
   * {@link PojoPathFunctionManager#getFunction(String) named} <code>myFunction</code> should be invoked to
   * {@link #get(Object, String, PojoPathContext) get}, {@link #create(Object, String, PojoPathContext)
   * create} or {@link #set(Object, String, Object, PojoPathContext) set} the value for
   * <code>&#64;myFunction</code> based on the result retrieved for <code>foo.bar</code>.
   * 
   * @see PojoPath#getFunction()
   */
  char FUNCTION_NAME_PREFIX = '@';

  /**
   * This method determines if this {@link PojoPathFunction} is deterministic. In this case it has to
   * guarantee that repetitive calls of {@link #get(Object, String, PojoPathContext) get} with the same
   * (unmodified) actual {@link net.sf.mmm.util.pojo.api.Pojo} will produce the same result.<br>
   * Typically a {@link PojoPathFunction} should be deterministic. However in some cases the calculation of a
   * {@link PojoPathFunction} may depend on the current time or a random value and will therefore be
   * indeterministic.<br>
   * If a {@link PojoPathFunction} is indeterministic, the {@link PojoPathContext#getCache() caching} will
   * disabled for its result and further traversals.<br>
   * Of course this method has to be deterministic and should always return the same boolean result for the
   * same instance.
   * 
   * @return <code>true</code> if this function is deterministic, <code>false</code> otherwise.
   */
  boolean isDeterministic();

  /**
   * This method gets the input-type of this function. It is the type of the
   * {@link net.sf.mmm.util.pojo.api.Pojo}s this function operates on.
   * 
   * @return the input class.
   */
  Class<IN> getInputClass();

  /**
   * This method gets the output-type (or return-type) of this function. It is the type of the value this
   * function traverses to, starting from the {@link #getInputClass() input}-
   * {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @return the output class.
   */
  Class<VALUE> getValueClass();

  /**
   * This method gets the value of this function. It is invoked by {@link PojoPathNavigator}.
   * {@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get} independent of the
   * {@link PojoPathMode}. A regular implementation should only return what is already there. However in
   * specific cases this may NOT (initially) be available from the given {@link net.sf.mmm.util.pojo.api.Pojo}
   * <code>actual</code> and therefore be retrieved from somewhere else (e.g. a database using a primary key
   * given via a {@link PojoPathContext#getProperties() property} of the given <code>context</code>). Further
   * it can be legal to modify the <code>actual</code> {@link net.sf.mmm.util.pojo.api.Pojo} e.g. by attaching
   * the externally retrieved result.
   * 
   * @param input is the actual {@link net.sf.mmm.util.pojo.api.Pojo} where this function is invoked on.
   *        Typically the returned value should be retrieved via this object.
   * @param functionName is the name under which this {@link PojoPathFunction} was invoked via the
   *        {@link PojoPathNavigator} excluding the {@link #FUNCTION_NAME_PREFIX}.
   * @param context is the {@link PojoPathContext} providing additional context information. Objects traversed
   *        between <code>actual</code> and the returned value should be
   *        {@link PojoPathRecognizer#recognize(Object, PojoPath) recognized} via the
   *        {@link PojoPathContext#getRecognizer() recognizer}.
   * @return the value of this function or <code>null</code> if NOT available.
   */
  VALUE get(IN input, String functionName, PojoPathContext context);

  /**
   * This method creates an appropriate new value. It is invoked by {@link PojoPathNavigator}.
   * {@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get} if the mode is
   * {@link PojoPathMode#CREATE_IF_NULL} after {@link #get(Object, String, PojoPathContext) get} returned
   * <code>null</code>.<br>
   * A typical implementation may create a new instance of &lt;VALUE&gt; via the
   * {@link PojoPathContext#getPojoFactory() pojo-factory}. Further in most cases the created value instance
   * will be attached to the given <code>actual</code> {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param input is the actual {@link net.sf.mmm.util.pojo.api.Pojo} where this function is invoked on.
   *        Typically the returned value should be retrieved via this object.
   * @param functionName is the name under which this {@link PojoPathFunction} was invoked via the
   *        {@link PojoPathNavigator} excluding the {@link #FUNCTION_NAME_PREFIX}.
   * @param context is the {@link PojoPathContext} providing additional context information. Objects traversed
   *        between <code>actual</code> and the returned value should be
   *        {@link PojoPathRecognizer#recognize(Object, PojoPath) recognized} via the
   *        {@link PojoPathContext#getRecognizer() recognizer}.
   * @return the created value. It may be <code>null</code> if creation is NOT possible. However returning
   *         <code>null</code> here will cause the {@link PojoPathNavigator} to fail with an exception.
   */
  VALUE create(IN input, String functionName, PojoPathContext context);

  /**
   * This method sets the given <code>value</code> for the given <code>actual</code>
   * {@link net.sf.mmm.util.pojo.api.Pojo}.<br>
   * After this method has been successfully invoked, the method {@link #get(Object, String, PojoPathContext)
   * get} should return the same <code>value</code> for identical arguments.
   * 
   * @param input is the actual {@link net.sf.mmm.util.pojo.api.Pojo} where this function is invoked on.
   *        Typically the given <code>value</code> should be set in this object.
   * @param functionName is the name under which this {@link PojoPathFunction} was invoked via the
   *        {@link PojoPathNavigator} excluding the {@link #FUNCTION_NAME_PREFIX}.
   * @param value is the value to set.
   * @param context is the {@link PojoPathContext} providing additional context information. Objects traversed
   *        between <code>actual</code> and the returned value should be
   *        {@link PojoPathRecognizer#recognize(Object, PojoPath) recognized} via the
   *        {@link PojoPathContext#getRecognizer() recognizer}.
   * @return the previous value that has been replaced or <code>null</code>.
   */
  VALUE set(IN input, String functionName, VALUE value, PojoPathContext context);

}
