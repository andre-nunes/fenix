/*
 * Created on 22/Jul/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ServidorApresentacao.utils;

import java.util.ArrayList;
import java.util.Collection;

import DataBeans.InfoRole;
import ServidorApresentacao.TestCaseActionExecution;
import Util.RoleType;

/**
 * @author lmac1
 *
 */
public abstract class TestCasePresentationManagerPortal extends TestCaseActionExecution{

	/**
	 * @param testName
	 */
	public TestCasePresentationManagerPortal(String testName) {
		super(testName);
	}



	/* (non-Javadoc)
	 * @see ServidorApresentacao.TestCaseActionExecution#getAuthorizedRolesCollection()
	 */
	public Collection getAuthorizedRolesCollection() {
		Collection roles = new ArrayList();
		InfoRole infoRole = new InfoRole();
		infoRole.setRoleType(RoleType.MANAGER);
		roles.add(infoRole);
		return roles;
	}

	/* (non-Javadoc)
	 * @see ServidorApresentacao.TestCaseActionExecution#getServletConfigFile()
	 */
	protected String getServletConfigFile() {
		return "/WEB-INF/web.xml";
	}

	/* (non-Javadoc)
	 * @see ServidorApresentacao.TestCaseActionExecution#getRequestPathInfoPathAction()
	 */
	protected String getRequestPathInfoPathAction() {
		return "/manager";
	}

}

