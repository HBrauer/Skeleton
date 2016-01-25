package org.test.waitandnotify;

public class Stack {
	
	int[] data = new int[10];
	int count = 0;
	
	public synchronized void push(int value){
		while(count  == data.length){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		data[count++]  = value;
		notifyAll();
		
	}
	
	public synchronized int pop(){
		while(count == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notifyAll();
		return data[count--]; 
	}

}
