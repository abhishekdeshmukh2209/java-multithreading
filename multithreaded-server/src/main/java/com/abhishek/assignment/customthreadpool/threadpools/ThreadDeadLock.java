package com.abhishek.assignment.customthreadpool.threadpools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ThreadDeadLock {

	public static void main(String[] args) {

		final Object lock = new Object();
		final Object lock2 = new Object();

		Thread t1 = new Thread() {

			public void run() {

				synchronized (lock) {
					System.out.println("Printing from thread1");
					synchronized (lock2) {
						System.out.println("Printing from thread1");
					}
				}

			}

		};

		Thread t2 = new Thread() {

			public void run() {

				synchronized (lock) {
					System.out.println("printint from thread2");
					synchronized (lock2) {
						System.out.println("printint from thread2");
					}
				}

			}

		};

		t1.start();
		t2.start();

		double dexp = 12345678;
		System.out.printf("dexp: %f\n", dexp);

		double d = 12.222;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(7);
		System.out.println(nf.format(d));
		
		Locale loc = new Locale("","CN");
		System.out.println(loc.getDisplayCountry());
		
	}

}
