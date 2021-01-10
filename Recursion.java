/**
 * Homework 5: Concurrency and Recursion
 * @author Rehan Javaid, rj3dxu 
 *
 */

public class Recursion {

	/**
	 * The purpose of this method is to test whether or not a particular user entered String is a 
	 * palindrome (i.e. is the same when read forward and backwards)
	 * static -> Allows the method to be called without creating an instance of the Recursion class
	 * @param s, String to be tested on to see whether or not it is a palindrome
	 * @return true if the String is a palindrome, otherwise false
	 */
	public static boolean palindrome (String s) {
		//removing whitespace and dealing with case 
		s = s.replaceAll("\\s", ""); //removes whitespace (method to do so found on stackoverflow.com)
		s = s.toLowerCase(); //changes everything to lowercase 
		//base case for recursive method
		if (s.length() == 0 || s.length() == 1) {
			return true; 
		}
		//recursive case
		int index = 0; 
		if (s.charAt(index) == s.charAt(s.length()-1)) {
			return palindrome(s.substring(index+1, s.length()-1));
		}
		return false; 
	}

	/**
	 * The purpose of this method is to return an input String as another string with the order of its
	 * characters reversed 
	 * static -> Allows this method to be called without creating an instance of the Recursion class
	 * @param s, String to reverse the order of its characters of 
	 * @return A new string with the characters of the original string reversed
	 */
	public static String reverseString(String s) {
		//base cases
		if (s.length() == 0) {
			return "";
		}
		if (s.length() == 1) {
			return s; 
		}
		//recursive case
		int n = 0; 
		String letter = s.substring(s.length()-1,s.length());
		n++; 
		return letter + reverseString(s.substring(0,s.length()-n));
	}
	/**
	 * The purpose of this method is to determine how many handshakes occur between a room with a specific
	 * number of people in it
	 * @param n, The number of people in the room 
	 * @return an integer of the number of handshakes that are exchanged between n people
	 */
	public static int handShakes(int n) { 
		//base case
		if (n < 2) { 
			return 0; 
		}
		//recursive case
		int numHandShakes = 0; 
		numHandShakes = (n-1) + handShakes(n-1); 
		return numHandShakes; 
	}

	public static long ackermann(long m, long n) {
		//base case
		if (m == 0) {
			return n+1; 
		}
		//recursive cases
		if (m > 0 && n == 0) {
			return ackermann(m-1, 1);
		}
		else if (m > 0 && n > 0) {
			return ackermann(m-1, ackermann(m, n-1)); 
		}
		return m + n; 
	}
	/**
	 * Main method for testing the recursive methods 
	 */
	public static void main(String args[]) {
		//Testing "palindrome" method  
		System.out.println(palindrome("Was it a car or a cat I saw ")); //expected output: true, actual output: true
		System.out.println(palindrome("hello")); //expected output: false, actual output: false 

		//Testing "reverseString" method
		System.out.println(reverseString("Tokyo 2020")); //expected output: 0202 oykoT, actual output: 0202 oykoT
		System.out.println("wow"); //expected output: wow, actual output: wow

		//Testing "handShakes" method
		System.out.println(handShakes(6)); //expected output: 15, actual output: 15
		System.out.println(handShakes(2)); //expected output: 1; actual output: 1

		//Testing "ackermann" method
		System.out.println(ackermann(2,0)); //expected output: 3, actual output: 3
		System.out.println(ackermann(1,2)); //expected output: 4, actual output: 4
	}
}
