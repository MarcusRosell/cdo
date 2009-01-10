/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 **************************************************************************/
package org.eclipse.emf.internal.cdo.analyzer;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOFetchRule;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOFeature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Simon McDuff
 */
public class CDOFeatureAnalyzerModelBased extends CDOAbstractFeatureRuleAnalyzer
{
  CDOAnalyzerFeatureInfo featureInfos = new CDOAnalyzerFeatureInfo();

  public CDOFeatureAnalyzerModelBased()
  {
  }

  @Override
  public void doPreTraverseFeature(CDOObject cdoObject, CDOFeature feature, int index)
  {
  }

  @Override
  public void doPostTraverseFeature(CDOObject cdoObject, CDOFeature feature, int index, Object value)
  {
    if (didFetch())
    {
      featureInfos.activate(cdoObject.cdoClass(), feature);
    }
  }

  public CDOID getContext()
  {
    return CDOID.NULL;
  }

  public List<CDOFetchRule> getFetchRules(Collection<CDOID> ids)
  {
    fetchData();
    List<CDOFetchRule> rules = new ArrayList<CDOFetchRule>();
    rules.addAll(featureInfos.getRules(lastTraverseCDOObject.cdoClass(), lastTraverseFeature));
    return rules;
  }
}
