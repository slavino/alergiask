package com.hustaty.android.alergia.enums;

import java.util.ArrayList;
import java.util.List;

import com.hustaty.android.alergia.AlergiaskActivity;
import com.hustaty.android.alergia.R;

public enum Alergene {
	
	OVERALL(R.string.pollen_overall, 8),
	AMBROZIA(R.string.pollen_ambrozia, 3),
	BOROVICA(R.string.pollen_borovica, 1),
	BREZA(R.string.pollen_breza, 11),
	BUK(R.string.pollen_buk, 12),
	BURINA(R.string.pollen_burina, 7),
	CYPRUS_TIS(R.string.pollen_cyprus_tis, 4),
	LIESKA(R.string.pollen_lieska, 10),
	LIPNICA_TRAVY(R.string.pollen_lipnica_travy, 2),
	OLIVA(R.string.pollen_oliva, 14),
	PALINA(R.string.pollen_palina, 6),
	PRHLAVA(R.string.pollen_prhlava, 5),
	PLESNE_HUBY(R.string.pollen_plesne_huby, 9),
	VRBA(R.string.pollen_vrba, 13);
	
	private Integer alergeneName;
	private int alergeneNumber;
	
	private Alergene(Integer alergeneName, int alergeneNumber) {
		this.alergeneName = alergeneName;
		this.alergeneNumber = alergeneNumber;
	}

	public String getAlergeneName() {
		return AlergiaskActivity.getContext().getResources().getString(alergeneName);
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
