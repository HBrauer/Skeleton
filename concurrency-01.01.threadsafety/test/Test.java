package test;

import java.io.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.awt.Point;
import area.*;

public final class Test {
	private final Area area;
	private final Point translation = new Point(10, -10);
	private final static int factor = 2;
	private static final ConcurrentSkipListSet<String> names_written = new ConcurrentSkipListSet<String>();
	private static final int HOW_OFTEN = 10;
	
	public Test(Area a) {
		area = a;
	}

	private class TranslationSetter implements Runnable {
		private CountDownLatch endSignal;
		public TranslationSetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				System.out.println(Thread.currentThread().getName()
						+ ": translate "+ area.getLocation()
						+" by ["+ translation.x+ "," + translation.y + "] ");
				area.translate(translation);
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
			}
			endSignal.countDown();
		}
	}

	private class TranslationGetter implements Runnable {
		private CountDownLatch endSignal;
		public TranslationGetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				Point p = area.getLocation();
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
				PrintStream s;
				String errMsg = "";
				if (Checker.checkLocation(p, translation)) {
					s = System.out;
				} else {
					s = System.err;
					s.print(">>>> ERROR: ");
					errMsg = " inconsistent ";
				}
				s.println(Thread.currentThread().getName()
						+ ": "+errMsg+"location after translation by [" + translation.x
						+ "," + translation.y + "]: " + p);
			}
			endSignal.countDown();
		}
	}

	private class ResizeSetter implements Runnable {
		private CountDownLatch endSignal;
		public ResizeSetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				System.out.println(Thread.currentThread().getName()
						+": resize width/height "+ area.getWidth() + "/" + area.getHeight()
						+"  by factor " + factor);
				area.resize(factor);
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
			}
			endSignal.countDown();
		}
	}

	private class ResizeGetter implements Runnable {
		private CountDownLatch endSignal;
		public ResizeGetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				int w = area.getWidth();
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
				int h = area.getHeight();
				PrintStream s;
				String errMsg = "";
				if (Checker.checkWidthHeight(1,2,w,h)) {
					s = System.out;
				} else {
					s = System.err;
					s.print(">>>> SUSPECT: ");
					errMsg = " inconsistent ";
				}
				s.println(Thread.currentThread().getName()
						+ ": "+errMsg+"width/height after resize by factor " + factor
						+ ": " + w + "/" + h);

			}
			endSignal.countDown();
		}
	}

	private class RenameSetter implements Runnable {
		private CountDownLatch endSignal;
		public RenameSetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			int id = 0;
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				String currentName = area.getName();
				String newName = Thread.currentThread().getName() + "-" + id++;
				area.rename(newName);
				names_written.add(newName);
				System.out.println(Thread.currentThread().getName()
						+ ": rename from \""+currentName+"\" to \"" + newName+"\"");
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
			}
			endSignal.countDown();
		}
	}

	private class RenameGetter implements Runnable {
		private CountDownLatch endSignal;
		public RenameGetter(CountDownLatch endSignal){
			this.endSignal = endSignal;
		}
		public void run() {
			for (int i=0; i<HOW_OFTEN &&!Thread.interrupted(); i++){
				String currentName = area.getName();
				try {Thread.sleep(100);}  catch (InterruptedException e) {return;}
				PrintStream s = System.out;
				String errMsg = "";
				if (Checker.checkName(names_written, currentName)) 
					s = System.out;
				else {
					s = System.err; s.print(">>>> SUSPECT: "); 
					errMsg = "; was read, but never written";
				}

				s.println(Thread.currentThread().getName()
						+ ": found name \""+currentName+"\""+errMsg);
			}
			endSignal.countDown();
		}
	}

	private static void test(Runnable getter, Runnable setter
			, int howMany,CountDownLatch endSignal) {
		Thread[] getterThreads = new Thread[howMany];
		Thread[] setterThreads = new Thread[howMany];
		int cnt = 0;
		for (int i = 0; i < howMany; i++) {
			try {
				String s = getter.getClass().getName();
				getterThreads[i] = new Thread(getter, s.substring(s
						.indexOf('$') + 1)+ "-" + cnt++);
				getterThreads[i].setDaemon(true);
				getterThreads[i].start();

				s = setter.getClass().getName();
				setterThreads[i] = new Thread(setter, s.substring(s
						.indexOf('$') + 1)+ "-" + cnt);
				setterThreads[i].setDaemon(true);
				setterThreads[i].start();
			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println(i);
			}
		}
		try {
			endSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) throws InterruptedException {
		Area area = new NamedArea(
				Thread.currentThread().getName()+" (initial value)",
				new Point(0, 0), 1, 2);
		ExplicitLockTest t = new ExplicitLockTest(area);
		System.out.println("_____________________________________\n");
		System.out.println("Testing " + area.getClass().getName());
		System.out.println("_____________________________________\n");

		int howManyThreads = 3;
		CountDownLatch endSignal = new CountDownLatch(2*howManyThreads);
		test(t.new TranslationGetter(endSignal), t.new TranslationSetter(endSignal)
			, howManyThreads, endSignal);
		
		howManyThreads = 2;
		endSignal = new CountDownLatch(2*howManyThreads);
		test(t.new ResizeGetter(endSignal), t.new ResizeSetter(endSignal)
			, howManyThreads, endSignal);
		
		howManyThreads = 3;
		endSignal = new CountDownLatch(2*howManyThreads);
		test(t.new RenameGetter(endSignal), t.new RenameSetter(endSignal)
			, howManyThreads, endSignal);

		Thread.sleep(1000);
	}
}