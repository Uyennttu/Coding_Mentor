package coding.mentor;

import java.util.Scanner;

import coding.mentor.data.User;
import coding.mentor.db.Database;
import coding.mentor.service.CourseService;
import coding.mentor.service.UserService;

public class Main {
	static final int LOGIN = 1;
	static final int REGISTER = 2;

	static final int REGISTER_COURSE = 1;
	static final int RETURN_COURSE_LIST = 2;
	static final int VIEW_MENTORS = 3;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Database.initDB();
		CourseService courseService = new CourseService();
		UserService userService = new UserService();

		// LOGIN + REGISTER
		User loggedInUser = null;
		int selectedCommand;
		do {
			System.out.println("1.LOGIN");
			System.out.println("2.REGISTER");
			System.out.println("No Account? Please Register.");
			String id, password, name;
			selectedCommand = scanner.nextInt();
			scanner.nextLine();
			switch (selectedCommand) {
			case LOGIN: {
				System.out.println("Please enter your ID: ");
				id = scanner.nextLine();
				System.out.println("Please enter your password: ");
				password = scanner.nextLine();
				loggedInUser = userService.login(id, password);
				break;
			}
			case REGISTER: {
				System.out.println("Please create your ID: ");
				id = scanner.nextLine();
				System.out.println("Please enter your name: ");
				name = scanner.nextLine();
				System.out.println("Please enter your password: ");
				password = scanner.nextLine();
				userService.registerNewUser(id, name, password);
				break;
			}
			default: {
				break;
			}
			}

		} while (loggedInUser == null);

		// REGISTER + SHOW COURSE
		int selectedCourse;
		int selectedCommandForCourse;
		do {
			System.out.println("---------------------------");
			System.out.println("COURSE LIST: ");
			System.out.println("0. Show My Registered Course");
			courseService.showAllCourses();
			System.out.println("Which course are you interested in?");

			selectedCourse = scanner.nextInt();
			if (selectedCourse == 0) {
				userService.showRegisteredCourseToUser(loggedInUser);
				return;
			}
			System.out.println("--------------------");
			System.out.println("COURSE DETAILS:");
			courseService.showCourseDetails(selectedCourse);
			System.out.println("Press 1 to Register. 2 to return to the Course List. 3 to View Mentors.");
			selectedCommandForCourse = scanner.nextInt();
			switch (selectedCommandForCourse) {
			case REGISTER_COURSE: {
				System.out.println("--------------------");
				userService.registerNewCourse(loggedInUser, Database.COURSES_DB.get(selectedCourse - 1));
				System.out.println("COURSE DETAILS:");
				courseService.showCourseDetails(selectedCourse);
			}
			case RETURN_COURSE_LIST: {
				break;
			}
			case VIEW_MENTORS: {
				System.out.println("--------------------");
				courseService.showMentorByCourse(Database.COURSES_DB.get(selectedCourse - 1));
				break;
			}
			default: {
				break;
			}
			}
		} while (selectedCourse != 0 && selectedCommandForCourse != 3);
	}
}
