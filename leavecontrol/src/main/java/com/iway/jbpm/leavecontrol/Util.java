package com.iway.jbpm.leavecontrol;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class Util {

    public static <T> T getServiceComponent(Class<T> type) {
        ExoContainer c = ExoContainerContext.getCurrentContainer();
        Object service = c.getComponentInstanceOfType(type);
        if (service == null) {
            return null;
        } else {
            return (T) service;
        }
    }
}
