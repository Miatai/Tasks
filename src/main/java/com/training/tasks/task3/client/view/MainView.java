package com.training.tasks.task3.client.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

	public void startingView(BufferedWriter os, BufferedReader is) throws IOException {
		Scanner sc = new Scanner(System.in);
		boolean logged = false;
		String response;
		while (!logged) {
			System.out.println("Welcome to the archive");
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			System.out.println("0. Exit");
			System.out.println("Input your choice:");
			switch (getNumberFromConsole(sc, 0, 2)) {
			case 1:
				os.write("LOG_IN " + logInView(sc));
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.startsWith("SUCCESS")) {
					System.out.println("--------");
					System.out.println(response);
					System.out.println("--------");
					mainMenuView(sc, os, is);
				}
				if (response.startsWith("ERROR")) {
					System.err.println("--------");
					System.err.println(response);
					System.err.println("--------");
				}
				break;
			case 2:
				os.write("SIGN_UP " + signUpView(sc));
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.startsWith("CREATED")) {
					System.out.println("--------");
					System.out.println(response);
					System.out.println("--------");
					startingView(os, is);
				}
				if (response.startsWith("ERROR")) {
					System.err.println("--------");
					System.err.println(response);
					System.err.println("--------");
					startingView(os, is);
				}

				break;
			case 0:
				System.out.println("--------");
				System.out.println("BYE.");
				os.write("QUIT");
				os.newLine();
				os.flush();
				System.exit(0);
				break;
			default:
				System.err.println("invalid input");
				break;
			}
		}

		System.out.println("you're logged");
//		mainMenuView(sc);
//		os.write("TEST_LOGGED");
//		os.newLine();
//		os.flush();
//		System.out.println(is.readLine());
		sc.close();
	}

	private void mainMenuView(Scanner sc, BufferedWriter os, BufferedReader is) throws IOException {
		while (true) {
			System.out.println("Main menu");
			System.out.println("1. View all cases");
			System.out.println("2. View case by name");
			System.out.println("3. Modify case by id");
			System.out.println("4. Delete case by id");
			System.out.println("5. Add new case");
			System.out.println("0. Exit to starting view");
			System.out.println("Input your choice:");
			String response;
			switch (getNumberFromConsole(sc, 0, 5)) {
			case 1:
				os.write("GET_ALL_CASES");
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.isEmpty()) {
					System.err.println("EMPTY LINE");
				}
				System.out.println(response.replaceAll("Case", "\nCase"));
				break;
			case 2:
				System.out.println("Input case id: ");

				os.write("GET_CASE_BY_ID <id>" + sc.nextLine() + "</id>");
				os.newLine();
				os.flush();
				response = is.readLine();
				System.out.println(response);
				break;
			case 3:
				System.out.println("Input case id: ");
				String id = sc.nextLine();
				String modCase = createCase(sc);
				os.write("MOD_CASE " + "<id>" + id + "</id> " + modCase);
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.startsWith("SUCCESS")) {
					System.out.println("--------");
					System.out.println(response);
					System.out.println("--------");
					mainMenuView(sc, os, is);
				}
				if (response.startsWith("ERROR")) {
					System.err.println("--------");
					System.err.println(response);
					System.err.println("--------");
				}
				break;
			case 4:
				System.out.println("Input case id: ");
				os.write("DELETE_CASE " + "<id>" + sc.nextLine() + "</id>");
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.startsWith("SUCCESS")) {
					System.out.println("--------");
					System.out.println(response);
					System.out.println("--------");
					mainMenuView(sc, os, is);
				}
				if (response.startsWith("ERROR")) {
					System.err.println("--------");
					System.err.println(response);
					System.err.println("--------");
				}
				break;
			case 5:
				String newCase = createCase(sc);
				os.write("ADD_CASE " + newCase);
				os.newLine();
				os.flush();
				response = is.readLine();
				if (response.startsWith("SUCCESS")) {
					System.out.println("--------");
					System.out.println(response);
					System.out.println("--------");
					mainMenuView(sc, os, is);
				}
				if (response.startsWith("ERROR")) {
					System.err.println("--------");
					System.err.println(response);
					System.err.println("--------");
					// in start view
				}
				break;
			case 0:
				startingView(os, is);
				break;

			default:
				break;
			}
		}

	}

	private String createCase(Scanner sc) {
		StringBuilder caseStrBuilder = new StringBuilder();
		System.out.println("Input case fields:");
		System.out.println("First Name:");
		caseStrBuilder.append("<case>");
		caseStrBuilder.append("<firstName>");
		caseStrBuilder.append(sc.nextLine());
		caseStrBuilder.append("</firstName>");
		System.out.println("Last Name:");
		caseStrBuilder.append("<lastName>");
		caseStrBuilder.append(sc.nextLine());
		caseStrBuilder.append("</lastName>");
		System.out.println("Group:");
		caseStrBuilder.append("<grpoup>");
		caseStrBuilder.append(sc.nextLine());
		caseStrBuilder.append("</grpoup>");
		System.out.println("Course:");
		caseStrBuilder.append("<course>");
		caseStrBuilder.append(sc.nextLine());
		caseStrBuilder.append("</course>");
		System.out.println("Faculty:");
		caseStrBuilder.append("<faculty>");
		caseStrBuilder.append(sc.nextLine());
		caseStrBuilder.append("</faculty>");
		caseStrBuilder.append("</case>");
		return caseStrBuilder.toString();
	}

	private String logInView(Scanner sc) {
		StringBuilder loginBuilder = new StringBuilder();
		System.out.println("Log In View");
		System.out.println("email:");
		loginBuilder.append("<email>");
		loginBuilder.append(sc.nextLine());
		loginBuilder.append("</email>");
		System.out.println("password:");
		loginBuilder.append("<email>");
		loginBuilder.append(sc.nextLine());
		loginBuilder.append("</email>");
		return loginBuilder.toString();
	}

	private String signUpView(Scanner sc) {
		StringBuilder userStrBuilder = new StringBuilder();
		System.out.println("Sign Up");
		System.out.println("First Name:");
		userStrBuilder.append("<user>");
		userStrBuilder.append("<firstName>");
		userStrBuilder.append(sc.nextLine());
		userStrBuilder.append("</firstName>");
		System.out.println("Last Name:");
		userStrBuilder.append("<lastName>");
		userStrBuilder.append(sc.nextLine());
		userStrBuilder.append("</lastName>");
		System.out.println("Email:");
		userStrBuilder.append("<email>");
		userStrBuilder.append(sc.nextLine());
		userStrBuilder.append("</email>");
		System.out.println("Password:");
		userStrBuilder.append("<password>");
		userStrBuilder.append(sc.nextLine());
		userStrBuilder.append("</password>");
		userStrBuilder.append("</user>");
		return userStrBuilder.toString();
	}

	private int getNumberFromConsole(Scanner sc, int min, int max) {
		int number = -1;
		boolean isCurrentNumber = false;
		while (!isCurrentNumber) {
			try {
				number = Integer.parseInt(sc.nextLine());
			} catch (InputMismatchException e) {
				System.err.println("invalid type of input");
				continue;
			}
			if (number < min || number > max) {
				System.err.println("invalid input: out of range");
				continue;
			}
			isCurrentNumber = true;
		}
		return number;
	}
}
