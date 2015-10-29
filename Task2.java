import java.util.ArrayList;
import java.util.List;

public class Task2<T> {
	Object a = new Object();
	int maxCount = 0;
	List<T> l = new ArrayList<>();
	public Task2(int maxCount) {
		this.maxCount = maxCount;
	}
	public void push(T e) {
		synchronized(a) {
			if (l.size() != maxCount) {
				l.add(e);
				a.notifyAll();
				System.out.println("Object added");
			} else {
				boolean added = false;
				while (!added) {
					if (l.size() == maxCount) {
						System.out.println("Waiting for remove...");
							try {
								a.wait();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}						
					} else {	
						l.add(e);						
						added = true;
						a.notifyAll();
						System.out.println("Object added");
					}
				}
			}
		}		
	}
	public T pull() {
		synchronized(a) {
			boolean removed = false;
			T t = null;
			while (!removed) {
				if (l.isEmpty()) {
					System.out.println("Collection is empty...");
					try {
						a.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					t = l.get(0);
					l.remove(0);
					removed = true;
					a.notifyAll();
					System.out.println("Object removed");
				}
			}
			return t;
		}
	}
	public static void main(String[] args) {
		Task2<Object> t2 = new Task2<>(5);
		(new Thread(new Runnable(){
			public void run(){
				while (true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t2.push(new Object());
				}				
			}			
		})).start();
		(new Thread(new Runnable(){
			public void run(){
				while (true) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t2.pull();
				}
			}			
		})).start();
	}
}
