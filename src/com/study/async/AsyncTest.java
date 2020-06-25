package com.study.async;

public class AsyncTest {
	Thread thread;
	
	public AsyncTest() {
		thread = new Thread() {
			@Override
			public void run() {
				System.out.println("a");
			}
		};
		thread.start();
		System.out.println("b");
	}
	
	public static void main(String[] args) {
		new AsyncTest();
	}
}
