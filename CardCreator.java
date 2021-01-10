/**
 * Homework 5 Card Creator
 * 
 * This class defines the thread task that will
 * "come up with" and submit greeting card ideas
 * to the print queue.  We have added the code
 * necessary to read from the file, but it's up to
 * you to handle turning off the printer (keeping
 * track of how many threads are open) and adding
 * the read-in line from the inspiration file to
 * the queue.
 * 
 * @author Rehan Javaid, rj3dxu 
 *
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CardCreator implements Runnable {

	/**
	 * Print queue to add new card ideas
	 */
	private PrintQueue printQueue;

	/**
	 * Inspiration file name
	 */
	private String filename;

	/**
	 * Keeps track of the number of threads that are open 
	 */
	private static int threadCount = 1; //set to one for purposes of successfully terminating at the end

	/**
	 * A lock for the threadCount so that multiple threads cannot modify the threadCount variable at the same time
	 */
	private Lock threadCountLock; 

	/**
	 * CardCreator constructor
	 */	
	public CardCreator(PrintQueue d, String filename) {
		printQueue = d;
		this.filename = filename; 
		threadCountLock = new ReentrantLock(); //instantiating the lock 
	}

	/**
	 * Run method that is the main method for the thread
	 */
	@Override
	public void run() {
		threadCountLock.lock();
		threadCount++; //thread count is incremented based on number of running threads
		threadCountLock.unlock();
		Scanner s = null;
		try {
			s = new Scanner(new FileReader(filename));
			while (s.hasNextLine()) {
				String lineToAdd = s.nextLine(); //message obtained
				printQueue.enqueue(lineToAdd);  //added to queue 
				Thread.sleep(1000); //put to sleep for 1 second
			}
		} catch (IOException e) {
			System.out.println("Could not read file");
		} catch (InterruptedException e){

		}finally {
			if (s != null) {
				s.close();
			}
		}
		threadCountLock.lock();
		threadCount--; //thread count decremented so it becomes obvious when the last thread is running 
		threadCountLock.unlock(); 
		if (threadCount == 1) {
			printQueue.turnOff();
		}
	}
}