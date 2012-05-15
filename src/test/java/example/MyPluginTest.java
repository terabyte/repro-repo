package example;

import org.junit.Test;

public class MyPluginTest
{
    @Test
    public void testSomething() {
    	MyPlugin mp = new MyPlugin(null);
    	mp.onIssueEvent(null);
    }
}
