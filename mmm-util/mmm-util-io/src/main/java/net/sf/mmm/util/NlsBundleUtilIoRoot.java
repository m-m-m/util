/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface NlsBundleUtilIoRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.io.api.StreamClosedException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The stream is already closed!")
  NlsMessage errorStreamClosed();

  /**
   * @see net.sf.mmm.util.io.api.BufferExceedException
   *
   * @param length the given length.
   * @param capacity the maximum capacity.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Offset or length \"{length}\" exceeds buffer with capacity \"{capacity}\"!")
  NlsMessage errorBufferLengthExceed(@Named("length") int length, @Named("capacity") int capacity);

  /**
   * @see net.sf.mmm.util.file.api.FileAlreadyExistsException
   *
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" already exists!")
  NlsMessage errorFileAlreadyExists(@Named("file") String file, @Named("directory") boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileNotExistsException
   *
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" does not exist!")
  NlsMessage errorFileNotExists(@Named("file") String file, @Named("directory") boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileCreationFailedException
   *
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be created!")
  NlsMessage errorFileCreationFailed(@Named("file") String file, @Named("directory") boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileAttributeModificationFailedException
   *
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The attributes of the {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be modified!")
  NlsMessage errorFileAttributeModificationFailed(@Named("file") String file, @Named("directory") boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileDeletionFailedException
   *
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be deleted!")
  NlsMessage errorFileDeletionFailed(@Named("file") String file, @Named("directory") boolean directory);

}
