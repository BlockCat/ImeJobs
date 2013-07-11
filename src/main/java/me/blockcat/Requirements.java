package me.blockcat;

import me.blockcat.exceptions.IncorrectArgumentException;

public enum Requirements {
	
	PERMISSION("p"),
	NOPERMISSION("-p"),
	SLOTITEM("s"),
	NOSLOTITEM("-s");
	
	private String flag;

	public static Requirement getRequirementFromString(String s) throws IncorrectArgumentException {
		String flag = s.split(":")[0];
		String value = s.split(":")[1];
		return new Requirement(flag, value);
	}

	Requirements(String flag) {
		this.flag = flag;
	}
	
	
	
	public static class Requirement {
		
		public final String flag_;
		private String value = null;
		
		public Requirement(String flag, String value) {
			this.flag_ = flag;
			this.value = value;
		}
		
		public void setValue(String s) {
			value = s;
		}
		
		public String getValue() {
			return value;
		}
		
		public String getFlag() {
			return flag_;
		}	
	}
}
