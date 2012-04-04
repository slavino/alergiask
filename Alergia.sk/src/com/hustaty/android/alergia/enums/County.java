package com.hustaty.android.alergia.enums;

import java.util.ArrayList;
import java.util.List;

public enum County {

	BB("Banskobystrický kraj", 6),
	BA("Bratislavský kraj", 1),
	KE("Košický kraj", 8),
	NR("Nitriansky kraj", 4),
	PO("Prešovský kraj", 7),
	TN("Trenčiansky kraj", 3),
	TT("Trnavský kraj", 2),
	ZA("Žilinský kraj", 5);

	private String countyName;
	
	private int countyNumber;
	
	private County(String countyName, int countyNumber) {
		this.countyName = countyName;
		this.countyNumber = countyNumber;
	}
	
	/**
	 * Getter.
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * Getter.
	 * @return the countyNumber
	 */
	public int getCountyNumber() {
		return countyNumber;
	}


	public static List<String> getCounties() {
		List<String> result = new ArrayList<String>();
		result.add(BB.getCountyName());
		result.add(BA.getCountyName());
		result.add(KE.getCountyName());
		result.add(NR.getCountyName());
		result.add(PO.getCountyName());
		result.add(TT.getCountyName());
		result.add(TN.getCountyName());
		result.add(ZA.getCountyName());
		return result;
	}
	
	public static County getCountyByCountyName(String countyName) {
		for(County c : County.values()) {
			if(c.getCountyName().equals(countyName)) {
				return c;
			}
		}
		return null;
	}
	
	public static County getCountyByCountyId(int countyId) {
		for(County c : County.values()) {
			if(c.getCountyNumber() == countyId) {
				return c;
			}
		}
		return null;
	}
	
}
