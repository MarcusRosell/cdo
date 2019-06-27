package org.eclipse.emf.cdo.examples.server;

import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.security.Realm;
import org.eclipse.emf.cdo.security.Role;
import org.eclipse.emf.cdo.security.User;
import org.eclipse.emf.cdo.server.IQueryContext;
import org.eclipse.emf.cdo.server.IQueryHandler;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.security.ISecurityManager;
import org.eclipse.emf.cdo.server.security.ISecurityManager.RealmOperation;
import org.eclipse.emf.cdo.server.security.SecurityManagerUtil;
import org.eclipse.emf.cdo.spi.server.QueryHandlerFactory;

import org.eclipse.net4j.util.factory.ProductCreationException;

/**
 * @author Eike Stepper
 */
public class UserRolesQueryHandlerFactory extends QueryHandlerFactory
{
  public static final String LANGUAGE = "user_roles";

  public UserRolesQueryHandlerFactory()
  {
    super(LANGUAGE);
  }

  @Override
  public IQueryHandler create(String description) throws ProductCreationException
  {
    return new IQueryHandler()
    {
      public void executeQuery(CDOQueryInfo info, final IQueryContext context)
      {
        ISession session = context.getView().getSession();
        IRepository repository = session.getManager().getRepository();
        ISecurityManager securityManager = SecurityManagerUtil.getSecurityManager(repository);

        if (securityManager != null)
        {
          final String userID = session.getUserID();

          securityManager.read(new RealmOperation()
          {
            public void execute(Realm realm)
            {
              User user = realm.getUser(userID);

              for (Role role : user.getAllRoles())
              {
                String roleID = role.getId();
                if (!context.addResult(roleID))
                {
                  // maxResults has been reached.
                  break;
                }
              }
            }
          });
        }
      }
    };
  }
}
