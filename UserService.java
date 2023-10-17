package coding.mentor.service;

import java.util.ArrayList;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.data.User;
import coding.mentor.db.Database;

public class UserService {
	public void registerNewUser(String id, String name, String password) {
		for (User existingUser : Database.USERS_DB) {
			if (existingUser.getId().equals(id)) {
				System.out.println("ID already existed. Please Login.");
				return;
			}
		}
		User user = new User(id, name, password);
		Database.USERS_DB.add(user);
		System.out.println("Register Successfully.");
	}

	public User login(String id, String password) {
		for (User user : Database.USERS_DB) {
			if (user.getId().equals(id)) {
				if (user.getPassword().equals(password)) {
					user.failedCount = 0;
					System.out.println("Login successfully. Welcome " + user.getName());
					return user;
				}
				user.failedCount++;
				checkFailedCount(user, user.failedCount);
				return null;
			}
		}
		System.out.println("Invalid ID or password. Please try again.");
		return null;
	}
	
	public User checkFailedCount(User user, int failedCount) {
		if (user.failedCount > 2) {
			System.out.println("Your account is blocked.");					
			return null;
		}		
		System.out.println("Invalid ID or password. Please try again.");
		System.out.println("You have " + (3 - user.failedCount) + " attempt(s) left");				
		return null;
	}

	public void registerNewCourse(User user, Course registerCourse) {
		for (Course existingCourse : user.getRegisteredCourses()) {
			if (existingCourse.getName().equals(registerCourse.getName())) {
				System.out.println("You already registered this course.");
				return;
			}
		}
		user.getRegisteredCourses().add(registerCourse);
		System.out.println("Congrats! You successfully register course " + registerCourse.getName());
	}

	public void showRegisteredCourseToUser(User user) {
		System.out.println(user.getName() + "'s Registered Courses:");
		ArrayList<Course> registeredCourses = user.getRegisteredCourses();
		if (registeredCourses.size() < 1) {
			System.out.println("You have no courses to show.");
			return;
		}
		for (Course existingCourse : registeredCourses) {
			System.out.println(existingCourse.getName());
			for (Mentor mentor : existingCourse.getTeachingMentors()) {
				System.out.println("Mentor: " + mentor.getName());
			}
		}
		System.out.println();
	}
}
