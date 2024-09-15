//package memoryanalyzer.heapdump;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;


@SuppressWarnings("restriction")

public class HeapDump {

    private static void dumpHeap(String pid, String targetFile) throws Exception {
        
        VirtualMachine vm = VirtualMachine.attach(pid);
        
        String connectorAddress = vm.startLocalManagementAgent();
        
        JMXConnector c = JMXConnectorFactory.connect(new JMXServiceURL(connectorAddress));
        
        MBeanServerConnection sc = c.getMBeanServerConnection();
        
        sc.invoke(ObjectName.getInstance("com.sun.management:type=HotSpotDiagnostic"),
            "dumpHeap",
            new Object[] { targetFile, true }, new String[]{ "java.lang.String", "boolean" });
    }

    
    
    public static void main(String[] args) {

        String pid = 1;
        String targetFile = "./dump.hprof";

        dumpHeap( pid, targetFile);

    }
}



/* Performing heap dump of the same process:
public static void dumpHeap(String filePath, boolean live) throws IOException {
    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
      server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
    mxBean.dumpHeap(filePath, live);
} 
 

//-- Better example of performing heap dump using hotspot diagnostics:

private static void dumpHeap(String pid, String targetFile) throws Exception {
    VirtualMachine vm = VirtualMachine.attach(pid);
    String connectorAddress = vm.startLocalManagementAgent();
    JMXConnector c = JMXConnectorFactory.connect(new JMXServiceURL(connectorAddress));
    MBeanServerConnection sc = c.getMBeanServerConnection();
    sc.invoke(ObjectName.getInstance("com.sun.management:type=HotSpotDiagnostic"),
        "dumpHeap",
        new Object[] { targetFile, true }, new String[]{ "java.lang.String", "boolean" });
}





//And here an example of doing the heap dump from external process:

import com.sun.tools.attach.VirtualMachine;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.InputStream;

public class HeapDump {

    public static void main(String[] args) throws Exception {
        String pid = args[0];
        HotSpotVirtualMachine vm = (HotSpotVirtualMachine) VirtualMachine.attach(pid);

        try (InputStream in = vm.dumpHeap("/tmp/heapdump.hprof")) {
            byte[] buf = new byte[200];
            for (int bytes; (bytes = in.read(buf)) > 0; ) {
                System.out.write(buf, 0, bytes);
            }
        } finally {
            vm.detach();
        }
    }
}

 */
