/*
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.releng.internal.setup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

import org.osgi.framework.BundleContext;

/**
 * @author Eike Stepper
 */
public class Activator extends Plugin
{
  public static final String PLUGIN_ID = "org.eclipse.emf.cdo.releng.setup";

  public static final boolean SETUP_IDE = "true".equalsIgnoreCase(System.getProperty(
      "org.eclipse.emf.cdo.releng.setup.ide", "false"));

  private static Activator plugin;

  private static BundleContext bundleContext;

  private SetupTaskPerformer setupTaskPerformer;

  public Activator()
  {
  }

  @Override
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    bundleContext = context;
    plugin = this;

    try
    {
      if (SETUP_IDE)
      {
        setupTaskPerformer = new SetupTaskPerformer(false);
        setupTaskPerformer.perform();
      }
    }
    catch (Exception ex)
    {
      log(ex);
    }
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    try
    {
      if (setupTaskPerformer != null)
      {
        setupTaskPerformer.dispose();
        setupTaskPerformer = null;
      }
    }
    catch (Exception ex)
    {
      log(ex);
    }

    plugin = null;
    super.stop(context);
  }

  public static void log(String message)
  {
    log(message, IStatus.INFO);
  }

  protected static void log(String message, int severity)
  {
    plugin.getLog().log(new Status(severity, PLUGIN_ID, message));
  }

  public static void log(IStatus status)
  {
    plugin.getLog().log(status);
  }

  public static String log(Throwable t)
  {
    IStatus status = getStatus(t);
    log(status);
    return status.getMessage();
  }

  public static IStatus getStatus(Throwable t)
  {
    if (t instanceof CoreException)
    {
      CoreException coreException = (CoreException)t;
      return coreException.getStatus();
    }

    String msg = t.getLocalizedMessage();
    if (msg == null || msg.length() == 0)
    {
      msg = t.getClass().getName();
    }

    return new Status(IStatus.ERROR, PLUGIN_ID, msg, t);
  }

  public static BundleContext getBundleContext()
  {
    return bundleContext;
  }

  public static Activator getDefault()
  {
    return plugin;
  }
}