package com.iway.jbpm.leavecontrol;

import com.iway.jbpm.core.KnowledgeEngine;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.PortalContainer;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/18/13
 */
public class LeaveRequestPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        Writer w = response.getWriter();
        w.write("Hello");
        //w.flush();

        PortalContainer pcontainer = PortalContainer.getInstanceIfPresent();
        KnowledgeEngine knowledgeEngine = (KnowledgeEngine) pcontainer.getComponentInstanceOfType(KnowledgeEngine.class);
        w.write(knowledgeEngine == null ? "Service not started" : "Service started successfully");


    }
}
