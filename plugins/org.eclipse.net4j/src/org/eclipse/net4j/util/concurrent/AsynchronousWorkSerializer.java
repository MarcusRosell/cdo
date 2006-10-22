/***************************************************************************
 * Copyright (c) 2004, 2005, 2006 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.util.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 */
public class AsynchronousWorkSerializer implements WorkSerializer, Runnable
{
  private ExecutorService executorService;

  private Queue<Runnable> workQueue;

  private Occupation occupation = new Occupation();

  public AsynchronousWorkSerializer(ExecutorService executorService, Queue<Runnable> workQueue)
  {
    if (executorService == null)
    {
      throw new IllegalArgumentException("executorService == null");
    }

    this.executorService = executorService;
    this.workQueue = workQueue;
  }

  public AsynchronousWorkSerializer(ExecutorService executorService)
  {
    this(executorService, new ConcurrentLinkedQueue());
  }

  public ExecutorService getExecutorService()
  {
    return executorService;
  }

  public void addWork(Runnable work)
  {
    workQueue.add(work);

    // isOccupied can (and must) be called unsynchronized here
    if (!occupation.isOccupied())
    {
      synchronized (occupation)
      {
        occupation.setOccupied(true);
      }

      System.out.println(toString() + ": Notifying executor service");
      executorService.execute(this);
    }
  }

  /**
   * Executed in the context of the
   * {@link #getExecutorService() executor service}.
   * <p>
   */
  public void run()
  {
    synchronized (occupation)
    {
      Runnable work;
      while (occupation.isOccupied() && (work = workQueue.poll()) != null)
      {
        try
        {
          work.run();
        }
        catch (RuntimeException ex)
        {
          ex.printStackTrace();
        }
      }

      occupation.setOccupied(false);
    }
  }

  public void dispose()
  {
    if (occupation.isOccupied())
    {
      occupation.setOccupied(false);
    }

    workQueue.clear();
    workQueue = null;
    executorService = null;
  }

  @Override
  public String toString()
  {
    return "AsynchronousWorkSerializer[" + executorService + "]";
  }

  /**
   * @author Eike Stepper
   */
  private static class Occupation
  {
    private boolean occupied = false;

    public boolean isOccupied()
    {
      return occupied;
    }

    public void setOccupied(boolean occupied)
    {
      this.occupied = occupied;
    }
  }
}
