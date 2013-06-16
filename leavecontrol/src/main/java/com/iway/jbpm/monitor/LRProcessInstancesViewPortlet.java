package com.iway.jbpm.monitor;

import com.iway.jbpm.core.KnowledgeEngine;
import com.iway.jbpm.leavecontrol.Util;
import org.drools.runtime.process.ProcessInstance;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 6/16/13
 */
public class LRProcessInstancesViewPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {

        KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);
        Collection<ProcessInstance> activeProcessInstances = kEngine.getActiveProcessInstances();
        request.setAttribute("activeProcessInstances", activeProcessInstances);

        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/jsp/processInstances.jsp");
        dispatcher.forward(request, response);
    }
}
