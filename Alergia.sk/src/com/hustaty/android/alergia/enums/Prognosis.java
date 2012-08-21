package com.hustaty.android.alergia.enums;

import com.hustaty.android.alergia.AlergiaskActivity;
import com.hustaty.android.alergia.R;

public enum Prognosis {

	INCREASING("arrow_up", "Zvýšenie koncentrácie", R.string.arrow_up),
	STABLE_POSSIBLE_GROWTH("equal_up", "Koncentrácia ustálená,\n\t\t\t možné zvýšenie", R.string.equal_up),
	STABLE("equal", "Koncentrácia ustálená", R.string.equal),
	STABLE_POSSIBLE_REDUCTION("equal_down", "Koncentrácia ustálená,\n\t\t\t možné zníženie", R.string.equal_down),
	REDUCTION("arrow_down", "Zníženie koncentrácie", R.string.arrow_down),
	UNKNOWN("unknown", "Neznáma", R.string.unknown);

	private String description;
	private String humanReadableDescription;
	private Integer stringId;
	
	private Prognosis(String description, String humanReadableDescription, int stringId) {
		this.description = description;
		this.humanReadableDescription = humanReadableDescription;
		this.stringId = stringId;
	}

	public String getDescription() {
		return description;
	}
	
	public String getHumanReadableDescription() {
		return AlergiaskActivity.getContext().getString(stringId);
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
