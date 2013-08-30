/**
 */
package org.eclipse.emf.cdo.releng.setup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Buckminster Import Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getMspec <em>Mspec</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getTargetPlatform <em>Target Platform</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getBundlePool <em>Bundle Pool</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getBuckminsterImportTask()
 * @model
 * @generated
 */
public interface BuckminsterImportTask extends OneTimeSetupTask
{
  /**
   * Returns the value of the '<em><b>Mspec</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mspec</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mspec</em>' attribute.
   * @see #setMspec(String)
   * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getBuckminsterImportTask_Mspec()
   * @model
   * @generated
   */
  String getMspec();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getMspec <em>Mspec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mspec</em>' attribute.
   * @see #getMspec()
   * @generated
   */
  void setMspec(String value);

  /**
   * Returns the value of the '<em><b>Target Platform</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target Platform</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target Platform</em>' attribute.
   * @see #setTargetPlatform(String)
   * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getBuckminsterImportTask_TargetPlatform()
   * @model
   * @generated
   */
  String getTargetPlatform();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getTargetPlatform <em>Target Platform</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target Platform</em>' attribute.
   * @see #getTargetPlatform()
   * @generated
   */
  void setTargetPlatform(String value);

  /**
   * Returns the value of the '<em><b>Bundle Pool</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bundle Pool</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bundle Pool</em>' attribute.
   * @see #setBundlePool(String)
   * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getBuckminsterImportTask_BundlePool()
   * @model
   * @generated
   */
  String getBundlePool();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.releng.setup.BuckminsterImportTask#getBundlePool <em>Bundle Pool</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bundle Pool</em>' attribute.
   * @see #getBundlePool()
   * @generated
   */
  void setBundlePool(String value);

} // BuckminsterImportTask