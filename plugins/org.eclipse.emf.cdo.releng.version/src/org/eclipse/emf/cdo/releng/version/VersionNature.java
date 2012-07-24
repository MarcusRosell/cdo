/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.releng.version;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Eike Stepper
 */
public class VersionNature implements IProjectNature
{
  public static final String NATURE_ID = "org.eclipse.emf.cdo.releng.version.VersionNature";

  private IProject project;

  public VersionNature()
  {
  }

  public IProject getProject()
  {
    return project;
  }

  public void setProject(IProject project)
  {
    this.project = project;
  }

  public void configure() throws CoreException
  {
    IProjectDescription desc = project.getDescription();
    ICommand[] commands = desc.getBuildSpec();

    for (int i = 0; i < commands.length; ++i)
    {
      if (commands[i].getBuilderName().equals(VersionBuilder.BUILDER_ID))
      {
        return;
      }
    }

    ICommand[] newCommands = new ICommand[commands.length + 1];
    System.arraycopy(commands, 0, newCommands, 0, commands.length);
    ICommand command = desc.newCommand();
    command.setBuilderName(VersionBuilder.BUILDER_ID);
    newCommands[newCommands.length - 1] = command;
    desc.setBuildSpec(newCommands);
    project.setDescription(desc, null);
  }

  public void deconfigure() throws CoreException
  {
    Markers.deleteAllMarkers(project);

    IProjectDescription description = getProject().getDescription();
    ICommand[] commands = description.getBuildSpec();
    for (int i = 0; i < commands.length; ++i)
    {
      if (commands[i].getBuilderName().equals(VersionBuilder.BUILDER_ID))
      {
        ICommand[] newCommands = new ICommand[commands.length - 1];
        System.arraycopy(commands, 0, newCommands, 0, i);
        System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
        description.setBuildSpec(newCommands);
        project.setDescription(description, null);
        return;
      }
    }
  }
}
