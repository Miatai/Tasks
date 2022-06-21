package com.training.tasks.task3.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.training.tasks.task3.server.domain.Case;
import com.training.tasks.task3.server.domain.User;
import com.training.tasks.task3.server.domain.UserRole;
import com.training.tasks.task3.server.service.CaseService;
import com.training.tasks.task3.server.service.UserService;

//«адание 3: создайте клиент-серверное приложение УјрхивФ.
//ќбщие требовани€ к заданию:
//Х ¬ архиве хран€тс€ ƒела (например, студентов). јрхив находитс€ на сервере.
//Х  лиент, в зависимости от прав, может запросить дело на просмотр, внести в него изменени€, или создать новое дело.
//“ребовани€ к коду лабораторной работы:
//Х ƒл€ реализации сетевого соединени€ используйте сокеты.
//Х ‘ормат хранени€ данных на сервере Ц xml-файлы.
public class ArchiveServerRunner {
	public static void main(String args[]) throws IOException {
		ServerSocket listener = null;
		System.out.println("Server is waiting to accept user...");
		int clientNumber = 0;
		try {
			listener = new ServerSocket(6666);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while (true) {
				Socket socketOfServer = listener.accept();
				new ServiceThread(socketOfServer, clientNumber++).start();
			}
		} finally {
			listener.close();
		}
	}

	private static void log(String message) {
		System.out.println(message);
	}

	private static class ServiceThread extends Thread {
		private int id;
		private User currentUser;
		private Socket socketOfServer;
		private final UserService userService = new UserService();
		private final CaseService caseService = new CaseService();

		public ServiceThread(Socket socketOfServer, int clientNumber) {
			this.id = clientNumber;
			this.socketOfServer = socketOfServer;
			log("New connection with client# " + this.id + " at " + socketOfServer);
		}

		@Override
		public void run() {
			try {
				BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
				BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
				while (true) {
					String line = is.readLine();
					System.out.println("REQUEST:" + line);
					if (line.startsWith("SIGN_UP")) {
						Pattern userRegex = Pattern.compile("<user>(.*?)</user>", Pattern.DOTALL);
						Matcher userMatcher = userRegex.matcher(line);
						String userXML = "";
						while (userMatcher.find()) {
							userXML = userMatcher.group(0);
						}
						os.write(userService.signUpUser(new User(userXML, UserRole.USER)));
						os.newLine();
						os.flush();
					}
					if (line.startsWith("LOG_IN")) {
						try {
							Pattern userFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
							Matcher userFieldMatcher = userFieldRegex.matcher(line);
							List<String> userFields = new ArrayList<>();
							while (userFieldMatcher.find()) {
								userFields.add(userFieldMatcher.group(2));
							}
							userService.logInUser(userFields.get(0), userFields.get(1));

							Optional<User> gotenUser = userService.logInUser(userFields.get(0), userFields.get(1));
							if (gotenUser.isPresent()) {
								currentUser = gotenUser.get();
								os.write("SUCCESS");
								os.newLine();
								os.flush();
//								logged = true;
							} else {
								throw new RuntimeException(
										"ERROR: optional's isPresent is false. this exception shouldn't throw up");
							}

						} catch (RuntimeException e) {
							os.write(e.getMessage());
							os.newLine();
							os.flush();
						}
					}
					if (line.startsWith("ADD_CASE")) {

						if (currentUser.isAdmin()) {
							Pattern caseRegex = Pattern.compile("<case>(.*?)</case>", Pattern.DOTALL);
							Matcher caseMatcher = caseRegex.matcher(line);
							Pattern caseFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
							while (caseMatcher.find()) {
								String cs = caseMatcher.group(1);
								Matcher caseFieldMatcher = caseFieldRegex.matcher(cs);
								List<String> caseFields = new ArrayList<>();
								while (caseFieldMatcher.find()) {
									caseFields.add(caseFieldMatcher.group(2));
								}
								Case newCase = new Case(caseFields.get(0), caseFields.get(1),
										caseFields.get(2), caseFields.get(3), caseFields.get(4));
								os.write(caseService.addNewCase(newCase));
								os.newLine();
								os.flush();
							}
						} else {
							os.write("ERROR: you do not have permission to add new case");
							os.newLine();
							os.flush();
						}
					}
					if (line.startsWith("MOD_CASE")) {
						int id = -1;
						Case modCase = new Case();
						Pattern idRegex = Pattern.compile("<(id)>([^<>]+)</\\1>");
						Matcher idMatcher = idRegex.matcher(line);
						while (idMatcher.find()) {
							id = Integer.parseInt(idMatcher.group(2));
						}
						Pattern caseRegex = Pattern.compile("<case>(.*?)</case>", Pattern.DOTALL);
						Matcher caseMatcher = caseRegex.matcher(line);
						Pattern caseFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
						while (caseMatcher.find()) {
							String cs = caseMatcher.group(1);
							Matcher caseFieldMatcher = caseFieldRegex.matcher(cs);
							List<String> caseFields = new ArrayList<>();
							while (caseFieldMatcher.find()) {
								caseFields.add(caseFieldMatcher.group(2));
							}
							modCase = new Case(caseFields.get(0), caseFields.get(1),
									caseFields.get(2), caseFields.get(3), caseFields.get(4));
						}
						os.write(caseService.modifyCaseById(id, modCase));
						os.newLine();
						os.flush();

					}
					if (line.startsWith("DELETE_CASE")) {
						Pattern idRegex = Pattern.compile("<(id)>([^<>]+)</\\1>");
						Matcher idMatcher = idRegex.matcher(line);
						String response = "";
						while (idMatcher.find()) {
							response = caseService.deleteCaseById(Integer.parseInt(idMatcher.group(2)));
						}
						os.write(response);
						os.newLine();
						os.flush();

					}
					if (line.startsWith("GET_CASE_BY_ID")) {
						Pattern idRegex = Pattern.compile("<(id)>([^<>]+)</\\1>");
						Matcher idMatcher = idRegex.matcher(line);
						String response = "";
						while (idMatcher.find()) {
							System.out.println("matcher: " + idMatcher.group(2));
							response = caseService.getCaseById(idMatcher.group(2));
						}
						os.write(response);
						os.newLine();
						os.flush();
					}
					if (line.startsWith("GET_ALL_CASES")) {
						os.write(caseService.getAllCases());
						os.newLine();
						os.flush();
					}
					if (line.startsWith("QUIT")) {
						break;
					}
				}

			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
