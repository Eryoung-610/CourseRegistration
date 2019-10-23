import java.util.*;

public class InteractiveCourseDriver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Course Name : ");
		String name = input.nextLine();

		System.out.print("Enter Max Students On Roster : ");
		int maxRosterSize = Integer.parseInt(input.nextLine());

		System.out.print("Enter Max Students On Waitlist : ");
		int maxWaitlistSize = Integer.parseInt(input.nextLine());

		Course course = new Course(name, maxRosterSize, maxWaitlistSize);

		System.out.println("\nWelcome to the Course Registration system for \n" + course);

		// Need to have a method to ask user to enter information(Name/ID/Tuition)

		boolean runProgram = false;
		while (!runProgram) {
			Student student;
			boolean drop;
			boolean add;

			System.out.println("\n\nSelect an option\n-----------------");
			System.out.println("1 : Add a student");
			System.out.println("2 : Drop a student");
			System.out.println("3 : Print course");
			System.out.println("4 : Exit\n");
			int userSelection = Integer.parseInt(input.nextLine());

			switch (userSelection) {
			case 1:
				student = studentInformation();
				add = course.addStudent(student);
				System.out.println(student + (add ? " added successfully to either roster or waitlist" : " not added"));
				continue;
			case 2:
				student = studentInformation();
				drop = course.dropStudent(student);
				System.out.println(
						student + (drop ? " dropped successfully from either roster or waitlist" : " not dropped"));
				continue;
			case 3:
				System.out.println(course);
				continue;
			}
			runProgram = true;
		}

		System.out.println(course);
	}

	private static Student studentInformation() {
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the student name:");
		String name = input.nextLine();
		System.out.print("Enter the id name:");
		String id = input.nextLine();
		System.out.print("Student paid tuition (Y/N): ");
		String paidString = input.nextLine();
		boolean paidTuition = paidString.equalsIgnoreCase("y");

		return new Student(name, id, paidTuition);
	}
}
