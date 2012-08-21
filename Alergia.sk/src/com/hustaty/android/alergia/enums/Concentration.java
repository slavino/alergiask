package com.hustaty.android.alergia.enums;

import com.hustaty.android.alergia.AlergiaskActivity;
import com.hustaty.android.alergia.R;

public enum Concentration {
	
	NONE(R.string.none, 1),
	VERY_LOW(R.string.very_low, 2),
	LOW(R.string.low, 3),
	MIDDLE(R.string.middle, 4),
	HIGH(R.string.high, 5),
	VERY_HIGH(R.string.very_high, 6),
	UNKNOWN(R.string.concentration_unknown, 0);
	
	private Integer description;
	
	private int order;
	
	private Concentration(Integer description, int order) {
		this.description = description;
		this.order = order;
	}

	public String getDescription() {
		return AlergiaskActivity.getContext().getString(this.description);
	}

	public int getOrder() {
		return this.order;
	}
	
	public static Concentration getConcentrationByOrderNumber(int orderNumber) {
		for(Concentration concentration : Concentration.values()) {
			if(concentration.getOrder() == orderNumber) {
				return concentration;
			}
		}
		return null;
	}
	
}
