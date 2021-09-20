package intervalobject;

public class Course {

	private final String course_id;
	private final String name;
	private final String teacher;
	private final String teachingposition;
	private final int hoursperweek;

	public Course(String course_id, String name, String teacher, String teachingposition, int hoursperweek) {
		super();
		this.course_id = course_id;
		this.name = name;
		this.teacher = teacher;
		this.teachingposition = teachingposition;
		this.hoursperweek = hoursperweek;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + hoursperweek;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		result = prime * result + ((teachingposition == null) ? 0 : teachingposition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (course_id == null) {
			if (other.course_id != null)
				return false;
		} else if (!course_id.equals(other.course_id))
			return false;
		if (hoursperweek != other.hoursperweek)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		if (teachingposition == null) {
			if (other.teachingposition != null)
				return false;
		} else if (!teachingposition.equals(other.teachingposition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", name=" + name + ", teacher=" + teacher + ", teachingposition="
				+ teachingposition + ", hoursperweek=" + hoursperweek + "]";
	}

	public String getCourse_id() {
		return course_id;
	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getTeachingposition() {
		return teachingposition;
	}
	
	public int getHoursperweek() {
		return hoursperweek;
	}
}
