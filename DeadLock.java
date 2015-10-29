public class DeadLock {
	static Object a = new Object();
	static Object b = new Object();
	public static void main(String[] args) {
		(new Thread(new Runnable(){
			public void run() {
				synchronized(a) {
					System.out.println("Thread 1");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized(b) {
						System.out.println("Thread 1(2)");
					}
				}
			}			
		})).start();
		(new Thread(new Runnable(){
			public void run() {
				synchronized(b) {
					System.out.println("Thread 2");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized(a) {
						System.out.print("Thread 2(2)");
					}
				}
			}
		})).start();
	}
}