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
    BY(County.ZA,"Bytča", 70, 80),
    CA(County.ZA,"Čadca", 130, 8),
    DK(County.ZA,"Dolný Kubín", 280, 100),
    KM(County.ZA,"Kysucké Nové Mesto", 150, 57),
    LM(County.ZA,"Liptovský Mikuláš", 356, 174),
    MT(County.ZA,"Martin", 176, 164),
    NO(County.ZA,"Námestovo", 300, 30),
    RK(County.ZA,"Ružomberok", 266, 168),
    TR(County.ZA,"Turčianske Teplice", 146, 248),
    TS(County.ZA,"Tvrdošín", 375, 59),
    ZA(County.ZA,"Žilina", 88, 154),
	//Bratislavsky kraj
    BA(County.BA,"Bratislava", 207, 214),
    MA(County.BA,"Malacky", 187, 60),
    PK(County.BA,"Pezinok", 266, 141),
    SC(County.BA,"Senec", 309, 188),
    //Trnavsky kraj
    DS(County.TT,"Dunajská Streda", 250, 220),
    GA(County.TT,"Galanta", 300, 160),
    HC(County.TT,"Hlohovec", 340, 100),
    PN(County.TT,"Piešťany", 309, 50),
    SE(County.TT,"Senica", 160, 30),
    SI(County.TT,"Skalica", 140, 0),
    TT(County.TT,"Trnava", 217, 100),
    //Trenciansky kraj
    BN(County.TN,"Bánovce nad Bebravou", 300, 160),
    IL(County.TN,"Ilava", 270, 86),
    MY(County.TN,"Myjava", 80, 170),
    NM(County.TN,"Nové Mesto nad Váhom", 170, 170),
    PE(County.TN,"Partizánske", 314, 250),
    PB(County.TN,"Považská Bystrica", 360, 40),
    PD(County.TN,"Prievidza", 386, 184),
    PU(County.TN,"Púchov", 270, 30),
    TN(County.TN,"Trenčín", 220, 125),
    //Nitriansky kraj
    KN(County.NR,"Komárno", 180, 260),
    LV(County.NR,"Levice", 304, 145),
    NR(County.NR,"Nitra", 170, 96),
    NZ(County.NR,"Nové Zámky", 200, 190),
    SA(County.NR,"Šaľa", 120, 145),
    TO(County.NR,"Topoľčany", 200, 10),
    ZM(County.NR,"Zlaté Moravce", 260, 60),
    //Banskobystricky kraj
    BB(County.BB,"Banská Bystrica", 180, 14),
    BS(County.BB,"Banská Štiavnica", 105, 134),
    BR(County.BB,"Brezno", 280, 6),
    DT(County.BB,"Detva", 240, 98),
    KA(County.BB,"Krupina", 146, 178),
    LC(County.BB,"Lučenec", 265, 199),
    PT(County.BB,"Poltár", 308, 146),
    RA(County.BB,"Revúca", 400, 57),
    RS(County.BB,"Rimavská Sobota", 400, 186),
    VK(County.BB,"Veľký Krtíš", 184, 235),
    ZV(County.BB,"Zvolen", 160, 80),
    ZC(County.BB,"Žarnovica", 30, 120),
    ZH(County.BB,"Žiar nad Hronom", 80, 59);

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

		for(District district : District.values()) {
			if(district.getDistrictName().startsWith(districtName)
					|| ((replaceSpecialChars(district.getDistrictName())).startsWith(districtName))) {
				return district;
			}
		}

		return null;

	}
	
	private static String replaceSpecialChars(String original) {
		String[] accented =    new String[]{
				"ľ","š","č","ť","ž","ý","á","í","é","ú","ä","ô","ň","ď","ŕ",
				"Ľ","Š","Č","Ť","Ž","Ý","Á","Í","É","Ú",        "Ň","Ď","Ŕ"
				};
		String[] replacement = new String[]{
				"l","s","c","t","z","y","a","i","e","u","a","o","n","d","r",
				"L","S","C","T","Z","Y","A","I","E","U",        "N","D","R"
				};
		
		for(int i = 0 ; i < accented.length ; i++ ) {
			original = original.replace(accented[i], replacement[i]);
		}
		
		return original;
	}

}
