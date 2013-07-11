package me.blockcat;

import me.blockcat.exceptions.IncorrectArgumentException;

/**
 * In here, the rewards for having a stone in the inventory will be processed.
 *  Permissions will be like:  - p:Ime.permission.opme
 *  an Ability will be like:   - a:type
 * @author Zino
 *
 */

public enum Rewards {

	PERMISSION("p"),
	GROUP("g"),
	ABILITY("a");

	private String flag;

	Rewards(String flag) {
		this.flag = flag;
	}

	public static Power getPowersFromString(String s) throws IncorrectArgumentException {
		String flag = s.split(":")[0];
		String value = s.split(":")[1];
		Power pow = new Power(flag, value);
		try { 
			pow.setValue(s.split(":")[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IncorrectArgumentException();
		}
		return pow;
	}


	public static class Power {

		private String flag_ = null;
		private String value = null;

		public Power(String flag, String value) {
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
