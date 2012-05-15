package example;

import java.io.InputStream;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;

public class MyPlugin implements InitializingBean, DisposableBean {
	private static final String URL = "test.vm";
	
	private EventPublisher ep;
	private VelocityEngine ve;
	private CopyOfMyPlugin mp;
	
	public MyPlugin(EventPublisher ep) {
		this.ep = ep;
		ve = new VelocityEngine();
		mp = new CopyOfMyPlugin();
	}
	
	@EventListener
	public void onIssueEvent(IssueEvent ie) {
		InputStream one = this.getClass().getClassLoader().getResourceAsStream(URL);
		InputStream two = ve.getClass().getClassLoader().getResourceAsStream(URL);
		InputStream three = mp.getClass().getClassLoader().getResourceAsStream(URL);
		System.out.println("\n\n\n");
		System.out.println("First result: " + ((one == null) ? "null" : one.toString()));
		System.out.println("Second result: " + ((two == null) ? "null" : two.toString()));
		System.out.println("Third result: " + ((three == null) ? "null" : three.toString()));
		System.out.println("\n\n\n");
	}

	@Override
	public void destroy() throws Exception {
		if (ep != null) {
			ep.unregister(this);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (ep != null) {
			ep.register(this);
		}
	}
}
