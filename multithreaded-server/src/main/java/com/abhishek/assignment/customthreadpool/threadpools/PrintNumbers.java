package com.abhishek.assignment.customthreadpool.threadpools;

public class PrintNumbers {

	public static void main(String[] args) {

		for (int i = 1; i <= 100; i++) {
			
			if ((i % 3) == 0) {
				if ((i % 5) == 0) {
					System.out.println(i + " Karjat");
					continue;
				} else {
					System.out.println(i + " Pune");
					continue;
				}
			}
			if ((i % 5) == 0) {

				if ((i % 3) == 0) {
					System.out.println(i + " Karjat");
					continue;
				} else {
					System.out.println(i + " Mumbai");
					continue;
				}
			}

			System.out.println(i);

		}

	}

}
