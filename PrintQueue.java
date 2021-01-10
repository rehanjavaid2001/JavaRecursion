
/**
 * Homework 5 PrintQueue
 * 
 * Implement the class below as specified in the
 * homework 5 document.
 * 
 * @author Rehan Javaid, rj3dxu
 *
 */

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PrintQueue {

	private LinkedList<String> toPrint;     // the printer's list of documents
	private Lock documentChangeLock;  // a ReentrantLock lock
	private Condition hasPrintTask;   // a condition object
	private boolean isOn;             // boolean variable describing if the 
	// queue is on (accepting jobs)

	//  Handle locking and conditions around the
	//       enqueueing and dequeuing of documents
	//       in this PrintQueue's document list (toPrint)
	//       Note: See example in the zip folder 'Thread Example 6 - Bank Deadlock' 


	/**
	 * Constructor: instantiates fields toPrint, isOn, documentChangeLock, and hasPrintTask
	 */
	public PrintQueue() {
		toPrint = new LinkedList<String>(); // create the list of documents
		isOn = true; // turn on the print queue
		documentChangeLock = new ReentrantLock(); 
		hasPrintTask = documentChangeLock.newCondition();
		// Complete instantiation of documentChangeLock and hasPrintTask here
	}


	/**
	 * dequeue
	 * The purpose of this method is to remove from the queue based on how the queue works
	 * (i.e. First in = First out)
	 * 
	 * @return null if the queue is empty and the element that is removed otherwise
	 */
	public String dequeue() throws InterruptedException{
		documentChangeLock.lock();
		try { 
			while (toPrint.size() == 0) { //condition for if the queue is empty
				return null;  
			}
			if (isOn == true) { //condition for if the queue is on 
				hasPrintTask.await(); 
			}
			return toPrint.remove(); // return the first document
		} finally {
			documentChangeLock.unlock();
		}
	}


	/**
	 * enqueue
	 * The purpose of this method is to add to the queue by adding a new message for the cards to the 
	 * end of the queue
	 * 
	 * @param String s, The message to be added to the PrintQueue 
	 */
	public void enqueue(String s){
		documentChangeLock.lock();
		try {
			toPrint.add(s); // add to the list of documents
			hasPrintTask.signalAll(); // signals back to dequeue so that it is no longer waiting
		}
		finally
		{
			documentChangeLock.unlock();
		} 
	}

	/**
	 * turnOff
	 * The purpose of this method is to turn off the print queue so that the print queue does not accept
	 * any more jobs. This method should be called by the last running CardCreator thread so that the printer
	 * threads are able to stop
	 */
	public void turnOff() {
		documentChangeLock.lock();
		try {
			hasPrintTask.signalAll(); //signals dequeue that it no longer needs to wait 
			isOn = false; 
		}finally {
			documentChangeLock.unlock();
		}
	}


	/**
	 * isOn
	 * isOn() is a method used to check the operating status of the PrintQueue
	 * @return boolean, returns true if the PrintQueue is on, otherwise returns false
	 */
	public boolean isOn() {
		if (isOn == true) {
			return true; 
		}
		return false; 
	}	

}