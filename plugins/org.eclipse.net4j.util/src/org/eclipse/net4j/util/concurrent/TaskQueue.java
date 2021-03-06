package org.eclipse.net4j.util.concurrent;

import org.eclipse.net4j.internal.util.bundle.OM;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A queue that asynchronously, but orderly, {@link #execute(Object, IProgressMonitor) executes}
 * tasks that have been {@link #schedule(Object) scheduled} to it.
 *
 * @author Eike Stepper
 * @since 3.13
 */
public abstract class TaskQueue<T>
{
  private final Set<T> queue = new LinkedHashSet<>();

  private Job job;

  public TaskQueue()
  {
  }

  public void schedule(T task)
  {
    synchronized (queue)
    {
      queue.remove(task);

      if (job != null && job.currentTask == task)
      {
        try
        {
          job.cancel();
        }
        finally
        {
          job = null;
        }
      }

      if (job != null)
      {
        queue.add(task);
      }
      else
      {
        job = new Job(task);
        job.schedule();
      }
    }
  }

  protected String getJobName(T task)
  {
    return "Execute " + task;
  }

  protected void handleException(T task, Exception ex)
  {
    OM.LOG.error(getJobName(task), ex);
  }

  protected abstract void execute(T task, IProgressMonitor monitor) throws Exception;

  /**
   * @author Eike Stepper
   */
  private final class Job extends org.eclipse.core.runtime.jobs.Job
  {
    private T currentTask;

    public Job(T task)
    {
      super(getJobName(task));
      currentTask = task;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor)
    {
      try
      {
        execute(currentTask, monitor);
        return Status.OK_STATUS;
      }
      catch (OperationCanceledException ex)
      {
        return Status.CANCEL_STATUS;
      }
      catch (Exception ex)
      {
        handleException(currentTask, ex);
        return Status.OK_STATUS;
      }
      finally
      {
        synchronized (queue)
        {
          if (queue.isEmpty() || monitor.isCanceled())
          {
            currentTask = null;
            job = null;
          }
          else
          {
            // Dequeue first task.
            Iterator<T> iterator = queue.iterator();
            currentTask = iterator.next();
            iterator.remove();

            // Reschedule current job.
            setName("Resolve " + getJobName(currentTask));
            schedule();
          }
        }
      }
    }
  }
}
