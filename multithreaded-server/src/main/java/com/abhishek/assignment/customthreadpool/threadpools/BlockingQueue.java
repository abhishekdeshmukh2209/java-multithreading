package com.abhishek.assignment.customthreadpool.threadpools;

interface BlockingQueue<E> {

      void put(E item)  throws InterruptedException ;

      E take()  throws InterruptedException;

      int size();
 
}