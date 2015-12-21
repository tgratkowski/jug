package org.jug.zgora;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;

public class Example4Main {

	public static void main(String[] args) {

		//
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		
		ksession.startProcess("org.jug.zgora.sample4");

		 manager.disposeRuntimeEngine(engine);
		 System.exit(0);
	}



	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(emf).knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "org.jug.zgora:example:1.0");
	}

	protected static void printEndLOG(KieSession ksession, TaskService taskService, ProcessInstance proc) {
		System.out.println("\n");
		Example4Main.printSingleEndLOG(ksession, taskService, proc);

		System.out.println("#########################################################################################################");
		System.out.println("\t ALL Processes [" + ksession.getProcessInstances().size() + "] in KSESSION ID:" + ksession.getIdentifier());
		for (ProcessInstance procIn : ksession.getProcessInstances()) {
			Example4Main.printSingleEndLOG(ksession, taskService, procIn);
		}
		System.out.println("#########################################################################################################\n");
	}

	protected static void printSingleEndLOG(KieSession ksession, TaskService taskService, ProcessInstance proc) {
		System.out.println("=========================================================================================================");
		System.out.println("KSESSION ID:" + ksession.getIdentifier());
		System.out.println("proc id: " + proc.getId() + " ProcessId: " + proc.getProcessId() + " State: " + proc.getState());
		System.out.println("STATES: STATE_PENDING = 0; STATE_ACTIVE = 1; STATE_COMPLETED = 2; STATE_ABORTED = 3; STATE_SUSPENDED = 4;");
		System.out.println("---------------------------------------------------------------------------------------------------------");
		for (Long id : taskService.getTasksByProcessInstanceId(proc.getId())) {
			System.out.print("taskID: " + id);
			System.out.print(" task name: " + taskService.getTaskById(id).getNames().get(0).getText());
			System.out.println("  Task Status: " + taskService.getTaskById(id).getTaskData().getStatus());
		}
		System.out.println("=========================================================================================================");

	}

}