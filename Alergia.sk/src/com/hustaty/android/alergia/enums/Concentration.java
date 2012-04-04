package com.hustaty.android.alergia.enums;

public enum Concentration {
	NONE("Nulová", 1),
	VERY_LOW("Veľmi nízka", 2),
	LOW("Nízka", 3),
	MIDDLE("Stredná", 4),
	HIGH("Vysoká", 5),
	VERY_HIGH("Veľmi vysoká", 6);
	
	private String description;
	
	private int order;
	
	private Concentration(String description, int order) {
		this.description = description;
		this.order = order;
	}

	public String getDescription() {
		return this.description;
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
