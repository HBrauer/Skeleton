package test;

public final class TaskContent implements Comparable<TaskContent> {
	private int taskId;
	private long terminationTimeStamp;
	
	public TaskContent(int taskId, long terminationTimeStamp) {
		super();
		this.taskId = taskId;
		this.terminationTimeStamp = terminationTimeStamp;
	}

	public int getTaskId() {
		return taskId;
	}
	public long getTerminationTimeStamp() {
		return terminationTimeStamp;
	}

	public int compareTo(TaskContent other) {
		if (this.terminationTimeStamp == 0 && other.terminationTimeStamp != 0) return 1;
		if (this.terminationTimeStamp != 0 && other.terminationTimeStamp == 0) return -1;
		if (this.terminationTimeStamp == 0 && other.terminationTimeStamp == 0) {
			if (this.taskId < other.taskId) return -1;
			if (this.taskId > other.taskId) return  1;
			return 0;
		}
		if (this.terminationTimeStamp < other.terminationTimeStamp) return -1;
		if (this.terminationTimeStamp > other.terminationTimeStamp) return  1;
		return 0;
	}

	public String toString() {
		return taskId + "/" + terminationTimeStamp;
	}
}
