package search;

public final class ElementType {
	private int id;
	private StringBuilder content;

	public ElementType(int id, StringBuilder content) {
		this.id = id;
		this.content = content;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.toString().hashCode());
		result = prime * result + id;
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
		ElementType other = (ElementType) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.toString().equals(other.content.toString()))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	public int getImmutablePart() {
		return id;
	}

	public StringBuilder getMutablePart() {
		return content;
	}
	public void setMutablePart(StringBuilder content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ElementType [immutable="+id+", mutable=" + content + "]";
	}


}
