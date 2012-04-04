package com.hustaty.android.alergia.enums;

public enum Prognosis {

	INCREASING("arrow_up"),
	STABLE_POSSIBLE_GROWTH("equal_up"),
	STABLE("equal"),
	STABLE_POSSIBLE_REDUCTION("equal_down"),
	REDUCTION("arrow_down");

	private String description;
	
	private Prognosis(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public static Prognosis getPrognosisByDescription(String description) {
		for(Prognosis prognosis : Prognosis.values()) {
			if(prognosis.description.equals(description)) {
				return prognosis;
			}
		}
		return null;
	}
	
}
