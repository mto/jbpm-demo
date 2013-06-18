package com.iway.jbpm.core;

import com.iway.jbpm.domain.LRRepository;
import com.iway.jbpm.iwayitems.LRCreateWorkItemHandler;
import com.iway.jbpm.iwayitems.LRUpdateWorkItemHandler;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.process.Process;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.exoplatform.commons.utils.PropertyManager;
import org.exoplatform.management.annotations.Impact;
import org.exoplatform.management.annotations.ImpactType;
import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.management.jmx.annotations.NameTemplate;
import org.exoplatform.management.jmx.annotations.Property;
import org.exoplatform.management.rest.annotations.RESTEndpoint;
import org.gatein.common.logging.Logger;
import org.gatein.common.logging.LoggerFactory;
import org.picocontainer.Startable;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/14/13
 */
@Managed
@NameTemplate({@Property(key = "view", value = "portal"), @Property(key = "service", value = "iwayJBPM"), @Property(key = "type", value = "skin")})
@ManagedDescription("IWay Demo Knowledge Engine")
@RESTEndpoint(path = "iwayjbpm")
public class KnowledgeEngine implements Startable {

    private static Logger LOGGER = LoggerFactory.getLogger(KnowledgeEngine.class);

    private final static String IWAY_JBPM_DEMO_RESOURCE_LOCATION_PARAM = "iway.jbpm.demo.resource";

    private final static String DEFAULT_PROCESS_DEFINITION_FILE = "leave-request-process.bpmn2";

    private final Lock lock = new ReentrantLock();

    private KnowledgeBase knowledgeBase;

    private final String resPath;

    private final LRRepository lrService;

    private StatefulKnowledgeSession ksession;

    public KnowledgeEngine(LRRepository _lrService) {
        String _resPath = PropertyManager.getProperty(IWAY_JBPM_DEMO_RESOURCE_LOCATION_PARAM);
        if (_resPath == null) {
            LOGGER.warn("Property " + IWAY_JBPM_DEMO_RESOURCE_LOCATION_PARAM + " is missing in gatein/conf/configuration.properties");
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL url = cl.getResource(DEFAULT_PROCESS_DEFINITION_FILE);
            _resPath = url.getPath();
        }
        lrService = _lrService;
        resPath = _resPath;
        knowledgeBase = buildKnowledgeBase(resPath);
    }

    @Override
    public void start() {
        ksession = knowledgeBase.newStatefulKnowledgeSession();
        ksession.getWorkItemManager().registerWorkItemHandler("CreateLR", new LRCreateWorkItemHandler(lrService));
        ksession.getWorkItemManager().registerWorkItemHandler("UpdateLR", new LRUpdateWorkItemHandler(lrService));
    }

    @Override
    public void stop() {
    }

    public Collection<Process> getProcesses() {
        Collection<Process> re;
        try {
            lock.lock();
            re = knowledgeBase.getProcesses();
        } finally {
            lock.unlock();
        }
        return re != null ? Collections.unmodifiableCollection(re) : re;
    }

    @Managed
    @ManagedDescription("List all process definitions")
    @Impact(ImpactType.READ)
    public String listProcesses() {
        StringBuilder b = new StringBuilder();
        for (Process p : getProcesses()) {
            b.append("ID: " + p.getId()).append(" Name: " + p.getName()).append("\n");
        }
        return b.toString();
    }

    @Managed
    @ManagedDescription("Rebuild knowledge base. Do this to have config modifications taken effect")
    @Impact(ImpactType.WRITE)
    public void reloadConfig() {
        KnowledgeBase kb = buildKnowledgeBase(resPath);
        if (kb != null) {
            try {
                lock.lock();
                knowledgeBase = kb;
                ksession = knowledgeBase.newStatefulKnowledgeSession();
                ksession.getWorkItemManager().registerWorkItemHandler("CreateLR", new LRCreateWorkItemHandler(lrService));
                ksession.getWorkItemManager().registerWorkItemHandler("UpdateLR", new LRUpdateWorkItemHandler(lrService));
            } finally {
                lock.unlock();
            }
        }
    }

    @Managed
    @ManagedDescription("Given a processID, execute relevant process!")
    @Impact(ImpactType.WRITE)
    public long executeProcess(@ManagedDescription("ID of executed process") @ManagedName("processID") String processID) {
        StatefulKnowledgeSession session = getStatefulSession();
        return session.startProcess(processID).getId();
    }

    /*
    @Managed
    @ManagedDescription("Register item handler, the bridge between JBPM service tasks and external services")
    @Impact(ImpactType.WRITE)
    public String registerWorkItemHandler(@ManagedDescription("Work Item Name") @ManagedName("workItem") String workItem, @ManagedDescription("Item Handler Type") @ManagedName("itemHandlerClazz") String itemHandlerClazz) {
        String message = "Succeed to register " + itemHandlerClazz + " under the name " + workItem;
        try {
            Class<? extends WorkItemHandler> clazz = Class.forName(itemHandlerClazz).asSubclass(WorkItemHandler.class);
            WorkItemHandler itemHandler = clazz.newInstance();
            registerWorkItemHandler(workItem, itemHandler);
        } catch (Exception ex) {
            message = ex.getMessage() + "\n Failed to register " + itemHandlerClazz + " under the name " + workItem;
        }
        return message;
    }

    public void registerWorkItemHandler(String workItemName, WorkItemHandler itemHandler) {
        getStatefulSession().getWorkItemManager().registerWorkItemHandler(workItemName, itemHandler);
    }
    */

    public long executeProcess(String processID, Map<String, Object> params) {
        StatefulKnowledgeSession session = getStatefulSession();
        return session.startProcess(processID, params).getId();
    }

    public void signalEvent(String type, Object payload) {
        StatefulKnowledgeSession session = getStatefulSession();
        session.signalEvent(type, payload);
    }

    public void signalEvent(String type, Object payload, long processInstanceID) {
        StatefulKnowledgeSession session = getStatefulSession();
        session.signalEvent(type, payload, processInstanceID);
    }

    public Collection<ProcessInstance> getActiveProcessInstances() {
        return getStatefulSession().getProcessInstances();
    }

    private KnowledgeBase buildKnowledgeBase(String filePath) {
        LOGGER.info("Build KnowledgeBase from config file: " + filePath);
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newFileResource(filePath), getResourceType(filePath));
        return builder.newKnowledgeBase();
    }

    private StatelessKnowledgeSession getStatelessSession() {
        StatelessKnowledgeSession session = null;
        try {
            lock.lock();
            session = knowledgeBase.newStatelessKnowledgeSession();

        } finally {
            lock.unlock();
        }
        return session;
    }

    private StatefulKnowledgeSession getStatefulSession() {
        return ksession;
    }

    private static ResourceType getResourceType(String filePath) {
        //Simply return BPMN2 for the moment
        return ResourceType.BPMN2;
    }
}
