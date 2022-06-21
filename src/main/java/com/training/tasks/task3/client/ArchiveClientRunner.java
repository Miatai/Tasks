package com.training.tasks.task3.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.training.tasks.task3.client.view.MainView;

public class ArchiveClientRunner {
	private static final MainView mainView = new MainView();

	public static void main(String[] args) {
		final String serverHost = "localhost";
		Socket socketOfClient = null;
		BufferedWriter os = null;
		BufferedReader is = null;
		try {
			socketOfClient = new Socket(serverHost, 6666);
			os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
			is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + serverHost);
			return;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + serverHost);
			return;
		}
		try {

			mainView.startingView(os, is);

			os.close();
			is.close();
			socketOfClient.close();
		} catch (UnknownHostException e) {
			System.err.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}
}
