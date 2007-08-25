/***************************************************************************
 * Copyright (c) 2004 - 2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.util.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Eike Stepper
 */
public abstract class SortedFileMap<K extends Comparable, V> implements Closeable
{
  private File file;

  private RandomAccessFile randomAccessFile;

  private ExtendedDataInput input;

  private ExtendedDataOutput output;

  private long entrySize;

  private long entryCount;

  /**
   * @see RandomAccessFile#RandomAccessFile(File, String)
   */
  public SortedFileMap(File file, String mode)
  {
    try
    {
      this.file = file;
      randomAccessFile = new RandomAccessFile(file, mode);
      input = new DataInputExtender(randomAccessFile);
      output = new DataOutputExtender(randomAccessFile);
      entrySize = getKeySize() + getValueSize();
      entryCount = randomAccessFile.length() / entrySize;
    }
    catch (IOException ex)
    {
      throw new IORuntimeException(ex);
    }
  }

  public void close() throws IOException
  {
    IOUtil.close(randomAccessFile);
  }

  public File getFile()
  {
    return file;
  }

  public RandomAccessFile getRandomAccessFile()
  {
    return randomAccessFile;
  }

  public long getEntryCount()
  {
    return entryCount;
  }

  public int getEntrySize()
  {
    return (int)entrySize;
  }

  public long getPosition(long index)
  {
    return index * entrySize;
  }

  public V get(K key)
  {
    try
    {
      long index = search(key);
      if (index < 0)
      {
        return null;
      }

      return readValue(input);
    }
    catch (IOException ex)
    {
      throw new IORuntimeException(ex);
    }
  }

  public V put(K key, V value)
  {
    try
    {
      long index = search(key);
      if (index >= 0)
      {
        long pos = randomAccessFile.getFilePointer();
        V oldValue = readValue(input);
        randomAccessFile.seek(pos);
        writeValue(output, value);
        return oldValue;
      }

      index = -index - 1;
      for (long i = entryCount; i > index; --i)
      {
        randomAccessFile.seek((i - 1) * entrySize);
        K k = readKey(input);
        V v = readValue(input);

        randomAccessFile.seek(i * entrySize);
        writeKey(output, k);
        writeValue(output, v);
      }

      ++entryCount;
      randomAccessFile.seek(getPosition(index));
      writeKey(output, key);
      writeValue(output, value);
      return null;
    }
    catch (IOException ex)
    {
      throw new IORuntimeException(ex);
    }
  }

  /**
   * @return The index of the entry with the given key if the key exists,
   *         <code>-(insertionIndex + 1)</code> otherwise.
   */
  protected long search(K key) throws IOException
  {
    long low = 0;
    long high = entryCount - 1;

    while (low <= high)
    {
      long mid = low + high >> 1;
      randomAccessFile.seek(mid * entrySize);
      Comparable midVal = readKey(input);
      int cmp = midVal.compareTo(key);

      if (cmp < 0)
        low = mid + 1;
      else if (cmp > 0)
        high = mid - 1;
      else
        return mid; // key found
    }
    return -(low + 1); // key not found.
  }

  public abstract int getKeySize();

  protected abstract K readKey(ExtendedDataInput in) throws IOException;

  protected abstract void writeKey(ExtendedDataOutput out, K key) throws IOException;

  public abstract int getValueSize();

  protected abstract V readValue(ExtendedDataInput in) throws IOException;

  protected abstract void writeValue(ExtendedDataOutput out, V value) throws IOException;
}
