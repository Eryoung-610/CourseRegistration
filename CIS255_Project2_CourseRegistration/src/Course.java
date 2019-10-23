import java.util.*;
import java.lang.*;

public class Course {
	private String name;
	private Student[] roster;
	private Student[] waitlist;

	private int maxOnRoster;
	private int maxOnWaitlist;
	private int totalInClass;
	private int totalOnWaitlist;

	public Course(String name, int maxOnRoster, int maxOnWaitlist) {
		this.name = name;
		this.maxOnRoster = maxOnRoster;
		this.maxOnWaitlist = maxOnWaitlist;
		this.totalInClass = 0;
		this.roster = new Student[maxOnRoster];
		this.waitlist = new Student[maxOnWaitlist];
	}

	public int getTotalInClass() {
		return this.totalInClass;
	}

	public int getTotalOnWaitlist() {
		return this.totalOnWaitlist;
	}

	public int getMaxOnRoster() {
		return this.maxOnRoster;
	}

	public int getMaxOnWaitlist() {
		return this.maxOnWaitlist;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean roomInClass() {
		return (this.totalInClass < this.maxOnRoster);
	}

	private boolean roomOnWaitlist() {
		return (this.totalOnWaitlist < this.maxOnWaitlist);
	}

	private boolean isAdded(Student student) {

		for (Student stud : this.roster) {
			if (stud == null) {
				return false;
			}

			if (stud.getID().equals(student.getID())) {
				return true;
			}
		}
		return false;
	}

	private boolean isWaitlisted(Student student) {

		for (Student stud : this.waitlist) {
			if (stud == null) {
				return false;
			}

			if (stud.getID().equals(student.getID())) {
				return true;
			}

		}
		return false;
	}

	// ELIGIBLE TO ADD IF : TUITION IS PAID, NOT IN CLASS OR WAITLIST. IF ROOM IN
	// CLASS THEN ADD, IF NOT THEN ADD TO WAITLIST, IF NOT, NO ADD AT ALL.

	public boolean addStudent(Student student) {
		if (student.isTuitionPaid()) {
			if (!isAdded(student) && !isWaitlisted(student)) {
				if (roomInClass()) {
					this.roster[this.totalInClass] = student;
					this.totalInClass++;
					return true;
				}
				if (roomOnWaitlist()) {
					this.waitlist[this.totalOnWaitlist] = student;
					this.totalOnWaitlist++;
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	// If student is added to the class or waitlisted, drop them.
	// NOTE TO SELF
	// ----------------
	// CREATE METHOD TO DELETE A STUDENT ELEMENT FROM THE CLASS THEN
	// READJUST THE LIST TO FILL SPOT. WOULD ALSO NEED TO GET THE SPECIFIC INDEX OF
	// THE STUDENT

	public boolean dropStudent(Student student) {

		if (isAdded(student)) {
			int rosterPosition = studentIndex(this.roster, student);
			removeFromList(this.roster, rosterPosition);
			this.totalInClass--;
			shiftWaitlist();
			return true;
		}
		if (isWaitlisted(student)) {
			int waitlistPosition = studentIndex(this.waitlist, student);
			removeFromList(this.waitlist, waitlistPosition);
			this.totalOnWaitlist--;
			return true;
		}
		return false;
	}

	// Move first person on waitlist up to the enrolled list.

	private void shiftWaitlist() {
		if (this.totalInClass < this.maxOnRoster && this.totalOnWaitlist > 0) {
			Student waitListedStudent = this.waitlist[0];
			removeFromList(this.waitlist, 0);
			this.totalOnWaitlist--;
			addStudent(waitListedStudent);
		}
	}

	// Loop through the array to find the specific students index on the list.

	private int studentIndex(Student[] list, Student student) {
		for (int i = 0; i < list.length; i++) {
			Student s = list[i];
			if (s != null) {
				if (s.getID().equals(student.getID())) {
					return i;
				}
			}
		}
		return 0;
	}

	private void removeFromList(Student[] list, int deleteElement) {

		list[deleteElement] = null; // Deleted Spot

		for (int i = deleteElement; i < list.length - 1; i++) {
			list[i] = list[i + 1]; // Shifts list up 1 from the spot where the element was deleted
		}

		list[list.length - 1] = null; // Last spot of index is now null
	}

	// Need name of course, students currently in class and max size of class,
	// students on waitlist and max size of waitlist, then current students in class
	// and waitlist.

	public String toString() {
		String s = this.name;
		s = s + "\n----------------------------" + "\n" + this.totalInClass + " Added (Maximum size of class "
				+ this.maxOnRoster + ")";
		s = s + printArray(this.roster);
		s = s + "\n" + this.totalOnWaitlist + " Waitlisted (Maximum size of waitlist " + this.maxOnWaitlist + ")";
		return s + printArray(this.waitlist);
	}

	private String printArray(Student[] list) {
		String s = "";
		for (Student stud : list) {
			if (stud != null) {
				s = s + "\n\t" + stud;
			}
		}
		return s;
	}

}
