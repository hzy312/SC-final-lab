package intervalobject;

public class MyProcess {

	private final String pid;
	private final String name;
	private final long shortest_exe_time;
	private final long longest_exe_time;

	public MyProcess(String pid, String name, long shortest_exe_time, long longest_exe_time) {
		super();
		this.pid = pid;
		this.name = name;
		this.shortest_exe_time = shortest_exe_time;
		this.longest_exe_time = longest_exe_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (longest_exe_time ^ (longest_exe_time >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + (int) (shortest_exe_time ^ (shortest_exe_time >>> 32));
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
		MyProcess other = (MyProcess) obj;
		if (longest_exe_time != other.longest_exe_time)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (shortest_exe_time != other.shortest_exe_time)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Process [pid=" + pid + ", name=" + name + "]";
	}

	public String getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public long getShortest_exe_time() {
		return shortest_exe_time;
	}

	public long getLongest_exe_time() {
		return longest_exe_time;
	}

}
