public class Robot {
	static Object lock = new Object();
	public static class RobotEx extends Thread {
		private String name;
       
		public RobotEx(String name) {
			this.name = name;
		}

		public void run() {
			synchronized(lock) {
				for (;;) {					
					step();				
					lock.notify();
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}						
		}

		private synchronized void step() {
			System.out.println("Step: " + name);
		}
	}

	public static void main(String... args) {
		new Thread(new RobotEx("left")).start();
		new Thread(new RobotEx("right")).start();
	}
}
