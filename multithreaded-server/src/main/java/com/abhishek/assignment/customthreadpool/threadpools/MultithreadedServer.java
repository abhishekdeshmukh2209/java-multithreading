package com.abhishek.assignment.customthreadpool.threadpools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import com.abhishek.assignment.customthreadpool.threadpools.ThreadPool;

//Main Class
public class MultithreadedServer {

	public static void main(String[] args) {
		try {
			new MultithreadedServer().startServer(Integer.valueOf(args[0]));
			FileLocation.location = args[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer(int port) throws IOException {
		final ThreadPool threadPool = new ThreadPool(5); // Unbounded queue
		// final ZymbraThreadPool threadPool=new ZymbraThreadPool(5,20); // Bounded queue
		final ServerSocket serverSocket = new ServerSocket(port);
		Runnable serverTask = new Runnable() {
			public void run() {
				try {
					System.out.println("Waiting for clients to connect...");
					while (true) {
						Socket clientSocket = serverSocket.accept();
						threadPool.execute(new ClientTask(clientSocket));
					}
				} catch (IOException e) {
					System.err.println("Unable to process client request");
					e.printStackTrace();
					try {
						serverSocket.close();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();

	}

	private class ClientTask implements Runnable {
		private final Socket clientSocket;

		private ClientTask(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

				byte[] byteArrray = null;
				StringBuilder requestBuilder = new StringBuilder();
				String contentBody = new String();
				while (in.ready()) {
					requestBuilder.append(in.readLine() + "\r\n");
				}

				System.out.println(requestBuilder);
				String[] requestLine = requestBuilder.toString().split("\r\n");
				List<String> requestArrayList = Arrays.asList(requestLine);
				int start = 0;
				int end = requestArrayList.size() - 1;
				String fileName = null;
				for (int i = 0; i < requestLine.length; i++) {
					if (requestArrayList.get(i).startsWith("Content-Disposition")) {
						fileName = requestArrayList.get(i).split("=")[2];
						start = i + 2;
					}
				}
				for (int i = start; i < end; i++) {
					contentBody += requestArrayList.get(i);
				}
				byteArrray = contentBody.getBytes();
				fileName = removeFirstandLast(fileName);

				OutputStream os = new FileOutputStream(new File(FileLocation.location + File.separator + fileName));
				os.write(byteArrray);
				os.close();

				out.write("HTTP/1.0 200 OK\r\n");
				out.write("Date: " + new Timestamp(System.currentTimeMillis()) + " GMT\r\n");
				out.write("Server: Zymbra File Processing\r\n");
				out.write("Content-Type: text/html\r\n");
				out.write("File-Name: " + fileName + "\r\n");
				out.write("File-Status: Uploaded\r\n");
				out.write("\r\n");
				out.write("<TITLE>File Status in headers</TITLE>\r\n");
				out.write("<P>" + fileName + "</P>");

				System.err.println("Connection terminated ! ! ");
				out.close();
				in.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String removeFirstandLast(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}

}