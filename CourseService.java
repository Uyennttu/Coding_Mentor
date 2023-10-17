package coding.mentor.service;

import coding.mentor.data.Course;
import coding.mentor.data.Mentor;
import coding.mentor.db.Database;

public class CourseService {
	public void showAllCourses() {
		for (int i = 0; i < Database.COURSES_DB.size(); i++) {
			System.out.print((i + 1) + ". " + Database.COURSES_DB.get(i).getName());
			for (Mentor mentor : Database.COURSES_DB.get(i).getTeachingMentors()) {
				System.out.print(" -- Mentor Name: " + mentor.getName());
			}
			System.out.println();
		}
	}

	public void showCourseDetails(int courseID) {
		for (int i = 0; i < Database.COURSES_DB.size(); i++) {
			Course course = Database.COURSES_DB.get(i);
			if (course.getId() == (courseID)) {
				System.out.println("Course Name: " + course.getName());
				System.out.println("Fee: " + course.getFee());
				System.out.print("Mentor Names: ");
				for (Mentor mentor : course.getTeachingMentors()) {
					System.out.println(mentor.getName() + " ");
				}			
				System.out.println("Begin: " + course.getBegin());
				System.out.println("End: " + course.getEnd());
				return;
			}
		}
	}

	public void showMentorByCourse(Course course) {
		System.out.println("Mentors of: " + course.getName() + " course");
		for (Mentor mentor : course.getTeachingMentors()) {
			System.out.println("Mentor Name: " + mentor.getName());
			System.out.println("Email: " + mentor.getEmail());
			System.out.println("Phone Number: " + mentor.getPhone());
		}
		System.out.println();
	}
}
