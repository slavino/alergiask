package com.hustaty.android.alergia.enums;

public enum Prognosis {

	INCREASING("arrow_up", "Zvýšenie koncentrácie"),
	STABLE_POSSIBLE_GROWTH("equal_up", "Koncentrácia ustálená, možné zvýšenie"),
	STABLE("equal", "Koncentrácia ustálená"),
	STABLE_POSSIBLE_REDUCTION("equal_down", "Koncentrácia ustálená, možné zníženie"),
	REDUCTION("arrow_down", "Zníženie koncentrácie");

	private String description;
	private String humanReadableDescription;
	
	private Prognosis(String description, String humanReadableDescription) {
		this.description = description;
		this.humanReadableDescription = humanReadableDescription;
	}

	public String getDescription() {
		return description;
	}
	
	
	public String getHumanReadableDescription() {
		return humanReadableDescription;
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
