import java.util.*;




public class MemoryConsumer {

            //public int allocationSize = 20 * 1024 * 1024; // 20 MB in bytes            
            public int allocationSize = 1 * 1024; // 1 KB in bytes            

            private ArrayList<byte[]> MemConsumerData = new ArrayList<>();

            public KostaClass kostaInstance; 
          
            public Professor prof;

            public MemoryConsumer() {
              //this.data = new byte[size]; // Allocate memory in the constructor based on size
              kostaInstance = new KostaClass(56);
              prof = new Professor();

            }


            public void addEl(){
                this.MemConsumerData.add(new byte[allocationSize]);
                return;
            }

        public static void main(String[] args) throws InterruptedException {

              long delay = 4 * 1000; // 4 seconds in milliseconds

              MemoryConsumer myKostaObject = new MemoryConsumer();
    
            while (true) {
                // Allocate a byte array
                //byte[] memoryBlock = new byte[allocationSize];

                myKostaObject.addEl();
    
                // Optional: Fill the array with a dummy value (encourages actual allocation)
                // Arrays.fill(memoryBlock, (byte) 1);
    
                System.out.println("Allocated " + myKostaObject.allocationSize / (1024 * 1024) + " MB");
    
                // Introduce a delay
                Thread.sleep(delay);
            }
        }
    }

/* runninin it in the commnad line:

javac MemoryConsumer.java
java -Xmx250m -XX:+UseZGC -XX:+ZGenerational -Xlog:gc=debug:file=gc.log MemoryConsumer

or with gc details:
java -Xmx250m -XX:+UseZGC -XX:+ZGenerational -Xlog:gc=debug:file=gc.log -XX:+PrintGCDetails MemoryConsumer
*/


/* javac MemoryConsumer.java KostaClass.java
/Users/kosta/Documents/repos/jdk/build/macosx-aarch64-server-release/images/jdk/bin/java -Xmx250m -XX:+UseZGC -XX:+ZGenerational -Xlog:gc=debug:file=gc.log -XX:+PrintGCDetails MemoryConsumer > gc.detailed2.log
*/
