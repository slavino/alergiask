package com.hustaty.android.alergia.enums;

import java.util.ArrayList;
import java.util.List;

public enum Alergene {
	
	OVERALL("Peľ (celkom)", 8),
	AMBROZIA("Ambrózia", 3),
	BOROVICA("Borovicovité", 1),
	BREZA("Brezovité", 11),
	BUK("Bukovité", 12),
	BURINA("Buriny", 7),
	CYPRUS_TIS("Cyprusovité a Tisovité", 4),
	LIESKA("Lieskovité", 10),
	LIPNICA_TRAVY("Lipnicovité trávy", 2),
	OLIVA("Olivovité", 14),
	PALINA("Palina", 6),
	PRHLAVA("Pŕhľavovité", 5),
	PLESNE_HUBY("Spóry plesní a húb", 9),
	VRBA("Vŕbovité", 13);
	
	private String alergeneName;
	private int alergeneNumber;
	
	private Alergene(String alergeneName, int alergeneNumber) {
		this.alergeneName = alergeneName;
		this.alergeneNumber = alergeneNumber;
	}

	public String getAlergeneName() {
		return alergeneName;
	}

	public int getAlergeneNumber() {
		return alergeneNumber;
	}

	public static List<String> getAlergenes() {
		List<String> result = new ArrayList<String>();
		for(Alergene alergene : Alergene.values()) {
			result.add(alergene.getAlergeneName());
		}
		return result;
	}
	
	public static Alergene getAlergeneByName(String alergeneName) {
		for(Alergene alergene : Alergene.values()) {
			if(alergene.getAlergeneName().equals(alergeneName)) {
				return alergene;
			}
		}
		return null;
	}

	public static Alergene getAlergeneById(int alergeneId) {
		for(Alergene alergene : Alergene.values()) {
			if(alergene.getAlergeneNumber() == alergeneId) {
				return alergene;
			}
		}
		return null;
	}
}
