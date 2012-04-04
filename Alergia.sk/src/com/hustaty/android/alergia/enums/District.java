package com.hustaty.android.alergia.enums;

import java.util.ArrayList;
import java.util.List;

public enum District {

	//Kosicky kraj
	GL(County.KE, "Gelnica", 160, 56),
	KE(County.KE, "Košice", 235, 110),
	MI(County.KE, "Michalovce", 385, 100),
	RV(County.KE, "Rožňava", 50, 120),
	SO(County.KE, "Sobrance", 470, 85),
	SN(County.KE, "Spišská Nová Ves", 80, 20),
	TV(County.KE, "Trebišov", 320, 150),
	//Presovsky kraj
	BJ(County.PO, "Bardejov", 230, 37),
	HE(County.PO, "Humenné", 400, 138),
	KK(County.PO, "Kežmarok", 90, 74),
	LE(County.PO, "Levoča", 100, 124),
	ML(County.PO, "Medzilaborce", 400, 80),
	PP(County.PO, "Poprad", 30, 110),
	PO(County.PO, "Prešov", 250, 136),
	SB(County.PO, "Sabinov", 197, 88),
	SV(County.PO, "Snina", 480, 134),
	SL(County.PO, "Stará Ľubovňa", 120, 20),
	SP(County.PO, "Stropkov", 360, 45),
	SK(County.PO, "Svidník", 300, 16),
	VT(County.PO, "Vranov nad Topľou", 327, 140),
	//Zilinsky kraj
    BY(County.ZA,"Bytča", 0, 0),
    CA(County.ZA,"Čadca", 0, 0),
    DK(County.ZA,"Dolný Kubín", 0, 0),
    KM(County.ZA,"Kysucké Nové Mesto", 0, 0),
    LM(County.ZA,"Liptovský Mikuláš", 0, 0),
    MT(County.ZA,"Martin", 0, 0),
    NO(County.ZA,"Námestovo", 0, 0),
    RK(County.ZA,"Ružomberok", 0, 0),
    TR(County.ZA,"Turčianske Teplice", 0, 0),
    TS(County.ZA,"Tvrdošín", 0, 0),
    ZA(County.ZA,"Žilina", 0, 0),
	//Bratislavsky kraj
    BA(County.BA,"Bratislava", 0, 0),
    MA(County.BA,"Malacky", 0, 0),
    PK(County.BA,"Pezinok", 0, 0),
    SC(County.BA,"Senec", 0, 0),
    //Trnavsky kraj
    DS(County.TT,"Dunajská Streda", 0, 0),
    GA(County.TT,"Galanta", 0, 0),
    HC(County.TT,"Hlohovec", 0, 0),
    PN(County.TT,"Piešťany", 0, 0),
    SE(County.TT,"Senica", 0, 0),
    SI(County.TT,"Skalica", 0, 0),
    TT(County.TT,"Trnava", 0, 0),
    //Trenciansky kraj
    BN(County.TN,"Bánovce nad Bebravou", 0, 0),
    IL(County.TN,"Ilava", 0, 0),
    MY(County.TN,"Myjava", 0, 0),
    NM(County.TN,"Nové Mesto nad Váhom", 0, 0),
    PE(County.TN,"Partizánske", 0, 0),
    PB(County.TN,"Považská Bystrica", 0, 0),
    PD(County.TN,"Prievidza", 0, 0),
    PU(County.TN,"Púchov", 0, 0),
    TN(County.TN,"Trenčín", 0, 0),
    //Nitriansky kraj
    KN(County.NR,"Komárno", 0, 0),
    LV(County.NR,"Levice", 0, 0),
    NR(County.NR,"Nitra", 0, 0),
    NZ(County.NR,"Nové Zámky", 0, 0),
    SA(County.NR,"Šaľa", 0, 0),
    TO(County.NR,"Topoľčany", 0, 0),
    ZM(County.NR,"Zlaté Moravce", 0, 0),
    //Banskobystricky kraj
    BB(County.BB,"Banská Bystrica", 0, 0),
    BS(County.BB,"Banská Štiavnica", 0, 0),
    BR(County.BB,"Brezno", 0, 0),
    DT(County.BB,"Detva", 0, 0),
    KA(County.BB,"Krupina", 0, 0),
    LC(County.BB,"Lučenec", 0, 0),
    PT(County.BB,"Poltár", 0, 0),
    RA(County.BB,"Revúca", 0, 0),
    RS(County.BB,"Rimavská Sobota", 0, 0),
    VK(County.BB,"Veľký Krtíš", 0, 0),
    ZV(County.BB,"Zvolen", 0, 0),
    ZC(County.BB,"Žarnovica", 0, 0),
    ZH(County.BB,"Žiar nad Hronom", 0, 0);

	private County county;
	
	private String districtName;
	
	private int x;
	
	private int y;
	
	private District(County county, String districtName, int x, int y) {
		this.county = county;
		this.districtName = districtName;
		this.x = x;
		this.y = y;
	}
	
	public County getCounty() {
		return this.county;
	}
	
	public String getDistrictName() {
		return districtName;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static List<String> getDistrictNamesByCounty(County county) {
		List<String> result = new ArrayList<String>();
		for(District district : District.values()) {
			if(district.getCounty().equals(county)) {
				result.add(district.getDistrictName());
			}
		}
		return result;
	}

	public static List<District> getDistrictsByCounty(County county) {
		List<District> result = new ArrayList<District>();
		for(District district : District.values()) {
			if(district.getCounty().equals(county)) {
				result.add(district);
			}
		}
		return result;
	}

	public static List<District> getDistrictsByCountyId(int countyId) {
		List<District> result = new ArrayList<District>();
		for(District district : District.values()) {
			if(district.getCounty().getCountyNumber() == countyId) {
				result.add(district);
			}
		}
		return result;
	}

	public static District getDistrictByDistrictName(String districtName) {
		for(District district : District.values()) {
			if(district.getDistrictName().equals(districtName)) {
				return district;
			}
		}
		return null;
	}
	
	
}
