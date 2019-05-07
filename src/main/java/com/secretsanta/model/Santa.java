package com.secretsanta.model;

public class Santa {
	private long id;
	
	private String santaname;
	
	private String santaspous;
	
	public Santa() {
	}

	public Santa(String santaname, String santaspous) {
		this.santaname = santaname;
		this.santaspous = santaspous;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSantaname() {
		return santaname;
	}

	public void setSantaname(String santaname) {
		this.santaname = santaname;
	}

	public String getSantaspous() {
		return santaspous;
	}

	public void setSantaspous(String santaspous) {
		this.santaspous = santaspous;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((santaname == null) ? 0 : santaname.hashCode());
		result = prime * result + ((santaspous == null) ? 0 : santaspous.hashCode());
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
		Santa other = (Santa) obj;
		if (id != other.id)
			return false;
		if (santaname == null) {
			if (other.santaname != null)
				return false;
		} else if (!santaname.equals(other.santaname))
			return false;
		if (santaspous == null) {
			if (other.santaspous != null)
				return false;
		} else if (!santaspous.equals(other.santaspous))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Santa [id=" + id + ", santaname=" + santaname + ", santaspous=" + santaspous + "]";
	}
	
	
}
