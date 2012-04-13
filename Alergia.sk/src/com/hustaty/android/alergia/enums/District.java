package com.hustaty.android.alergia.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hustaty.android.alergia.util.LogUtil;

public enum District {

	//Kosicky kraj
	GL(County.KE, "Gelnica", 160, 56, 48.8518087, 20.9339431),
	KE(County.KE, "Košice", 235, 110, 48.7209956, 21.2577477),
	MI(County.KE, "Michalovce", 385, 100, 48.7559954, 21.9148577),
	RV(County.KE, "Rožňava", 50, 120, 48.6628294, 20.533863),
	SO(County.KE, "Sobrance", 470, 85, 48.7458654, 22.1797662),
	SN(County.KE, "Spišská Nová Ves", 80, 20, 48.9431138, 20.5729469),
	TV(County.KE, "Trebišov", 320, 150, 48.6296066, 21.7197396),
	//Presovsky kraj
	BJ(County.PO, "Bardejov", 230, 37, 49.2946849, 21.2764409),
	HE(County.PO, "Humenné", 400, 138, 48.9338752, 21.9107213),
	KK(County.PO, "Kežmarok", 90, 74, 49.1373227, 20.4295429),
	LE(County.PO, "Levoča", 100, 124, 49.0260192, 20.5888751),
	ML(County.PO, "Medzilaborce", 400, 80, 49.276818, 21.903308),
	PP(County.PO, "Poprad", 30, 110, 49.0570032, 20.3047448),
	PO(County.PO, "Prešov", 250, 136, 49.002474, 21.2396841),
	SB(County.PO, "Sabinov", 197, 88, 49.1028212, 21.097855),
	SV(County.PO, "Snina", 480, 134, 48.9875164, 22.151957),
	SL(County.PO, "Stará Ľubovňa", 120, 20, 49.3022127, 20.6905301),
	SP(County.PO, "Stropkov", 360, 45, 49.2026191, 21.6518245),
	SK(County.PO, "Svidník", 300, 16, 49.3062472, 21.5683369),
	VT(County.PO, "Vranov nad Topľou", 327, 140, 48.8903354, 21.6829836),
	//Zilinsky kraj
	BY(County.ZA, "Bytča", 70, 80, 49.2231897, 18.5584905),
	CA(County.ZA, "Čadca", 130, 8, 49.4379367, 18.7897011),
	DK(County.ZA, "Dolný Kubín", 280, 100, 49.2090236, 19.2953678),
	KM(County.ZA, "Kysucké Nové Mesto", 150, 57, 49.301944, 18.7866296),
	LM(County.ZA, "Liptovský Mikuláš", 356, 174, 49.0829818, 19.6122332),
	MT(County.ZA, "Martin", 176, 164, 49.062838, 18.9218407),
	NO(County.ZA, "Námestovo", 300, 30, 49.406373, 19.4838841),
	RK(County.ZA, "Ružomberok", 266, 168, 49.081876, 19.3077347),
	TR(County.ZA, "Turčianske Teplice", 146, 248, 48.8636263, 18.8633405),
	TS(County.ZA, "Tvrdošín", 375, 59, 49.3335909, 19.5548618),
	ZA(County.ZA, "Žilina", 88, 154, 49.2195481, 18.7412735),
	//Bratislavsky kraj
	BA(County.BA, "Bratislava", 207, 214, 48.1462386, 17.1072618),
	MA(County.BA, "Malacky", 187, 60, 48.4363404, 17.0182641),
	PK(County.BA, "Pezinok", 266, 141, 48.2887323, 17.2678143),
	SC(County.BA, "Senec", 309, 188, 48.2209, 17.3986949),
    //Trnavsky kraj
	DS(County.TT, "Dunajská Streda", 250, 220, 47.9923894, 17.6170548),
	GA(County.TT, "Galanta", 300, 160, 48.1895413, 17.7266636),
	HC(County.TT, "Hlohovec", 340, 100, 48.4248462, 17.8040044),
	PN(County.TT, "Piešťany", 309, 50, 48.5892019, 17.834105),
	SE(County.TT, "Senica", 160, 30, 48.6765247, 17.3639576),
	SI(County.TT, "Skalica", 140, 0, 48.8460561, 17.2278639),
	TT(County.TT, "Trnava", 217, 100, 48.3735186, 17.5951127),
    //Trenciansky kraj
	BN(County.TN, "Bánovce nad Bebravou", 300, 160, 48.71915, 18.259244),
	IL(County.TN, "Ilava", 270, 86, 48.9985733, 18.2348862),
	MY(County.TN, "Myjava", 80, 170, 48.7504728, 17.5651643),
	NM(County.TN, "Nové Mesto nad Váhom", 170, 170, 48.7574636, 17.8311926),
	PE(County.TN, "Partizánske", 314, 250, 48.6264292, 18.3716216),
	PB(County.TN, "Považská Bystrica", 360, 40, 49.1173313, 18.4483676),
	PD(County.TN, "Prievidza", 386, 184, 48.7729271, 18.6221523),
	PU(County.TN, "Púchov", 270, 30, 49.1249356, 18.3301705),
	TN(County.TN, "Trenčín", 220, 125, 48.8939462, 18.0378496),
    //Nitriansky kraj
	KN(County.NR, "Komárno", 180, 260, 47.7625006, 18.1305954),
	LV(County.NR, "Levice", 304, 145, 48.2192418, 18.6065015),
	NR(County.NR, "Nitra", 170, 96, 48.3098916, 18.0858758),
	NZ(County.NR, "Nové Zámky", 200, 190, 47.9860672, 18.1640649),
	SA(County.NR, "Šaľa", 120, 145, 48.1514404, 17.8757207),
	TO(County.NR, "Topoľčany", 200, 10, 48.5584441, 18.171697),
	ZM(County.NR, "Zlaté Moravce", 260, 60, 48.3832287, 18.3976342),
    //Banskobystricky kraj
	BB(County.BB, "Banská Bystrica", 180, 14, 48.736277, 19.1461917),
	BS(County.BB, "Banská Štiavnica", 105, 134, 48.4586529, 18.8930346),
	BR(County.BB, "Brezno", 280, 6, 48.806356, 19.6438876),
	DT(County.BB, "Detva", 240, 98, 48.5603755, 19.4191434),
	KA(County.BB, "Krupina", 146, 178, 48.3542872, 19.0668757),
	LC(County.BB, "Lučenec", 265, 199, 48.3298602, 19.6637354),
	PT(County.BB, "Poltár", 308, 146, 48.4323391, 19.7932419),
	RA(County.BB, "Revúca", 400, 57, 48.6835829, 20.1143883),
	RS(County.BB, "Rimavská Sobota", 400, 186, 48.3833442, 20.018358),
	VK(County.BB, "Veľký Krtíš", 184, 235, 48.2073601, 19.3477728),
	ZV(County.BB, "Zvolen", 160, 80, 48.5758623, 19.1256291),
	ZC(County.BB, "Žarnovica", 30, 120, 48.4883608, 18.7077948),
	ZH(County.BB, "Žiar nad Hronom", 80, 59, 48.5882665, 18.8493777);
	
	//Kosicky kraj
//	GL(County.KE, "Gelnica", 160, 56),
//	KE(County.KE, "Košice", 235, 110),
//	MI(County.KE, "Michalovce", 385, 100),
//	RV(County.KE, "Rožňava", 50, 120),
//	SO(County.KE, "Sobrance", 470, 85),
//	SN(County.KE, "Spišská Nová Ves", 80, 20),
//	TV(County.KE, "Trebišov", 320, 150),
	//Presovsky kraj
//	BJ(County.PO, "Bardejov", 230, 37),
//	HE(County.PO, "Humenné", 400, 138),
//	KK(County.PO, "Kežmarok", 90, 74),
//	LE(County.PO, "Levoča", 100, 124),
	//ML(County.PO, "Medzilaborce", 400, 80, 49.276818, 21.903308),
	//PP(County.PO, "Poprad", 30, 110),
	//PO(County.PO, "Prešov", 250, 136),
	//SB(County.PO, "Sabinov", 197, 88),
	//SV(County.PO, "Snina", 480, 134),
	//SL(County.PO, "Stará Ľubovňa", 120, 20),
	//SP(County.PO, "Stropkov", 360, 45),
	//SK(County.PO, "Svidník", 300, 16),
	//VT(County.PO, "Vranov nad Topľou", 327, 140),
	//Zilinsky kraj
    //BY(County.ZA, "Bytča", 70, 80),
    //CA(County.ZA, "Čadca", 130, 8),
    //DK(County.ZA, "Dolný Kubín", 280, 100),
    //KM(County.ZA, "Kysucké Nové Mesto", 150, 57),
    //LM(County.ZA, "Liptovský Mikuláš", 356, 174),
    //MT(County.ZA, "Martin", 176, 164),
    //NO(County.ZA, "Námestovo", 300, 30),
    //RK(County.ZA, "Ružomberok", 266, 168),
    //TR(County.ZA, "Turčianske Teplice", 146, 248),
    //TS(County.ZA, "Tvrdošín", 375, 59),
    //ZA(County.ZA, "Žilina", 88, 154),
	//Bratislavsky kraj
//    BA(County.BA, "Bratislava", 207, 214),
//    MA(County.BA, "Malacky", 187, 60),
//    PK(County.BA, "Pezinok", 266, 141),
//    SC(County.BA, "Senec", 309, 188),
    //Trnavsky kraj
//    DS(County.TT, "Dunajská Streda", 250, 220),
//    GA(County.TT, "Galanta", 300, 160),
//    HC(County.TT, "Hlohovec", 340, 100),
//    PN(County.TT, "Piešťany", 309, 50),
//    SE(County.TT, "Senica", 160, 30),
//    SI(County.TT, "Skalica", 140, 0),
//    TT(County.TT, "Trnava", 217, 100),
    //Trenciansky kraj
//    BN(County.TN, "Bánovce nad Bebravou", 300, 160),
//    IL(County.TN, "Ilava", 270, 86),
//    MY(County.TN, "Myjava", 80, 170),
//    NM(County.TN, "Nové Mesto nad Váhom", 170, 170),
//    PE(County.TN, "Partizánske", 314, 250),
//    PB(County.TN, "Považská Bystrica", 360, 40),
//    PD(County.TN, "Prievidza", 386, 184),
//    PU(County.TN, "Púchov", 270, 30),
//    TN(County.TN, "Trenčín", 220, 125),
    //Nitriansky kraj
//    KN(County.NR, "Komárno", 180, 260),
//    LV(County.NR, "Levice", 304, 145),
//    NR(County.NR, "Nitra", 170, 96),
//    NZ(County.NR, "Nové Zámky", 200, 190),
//    SA(County.NR, "Šaľa", 120, 145),
//    TO(County.NR, "Topoľčany", 200, 10),
//    ZM(County.NR, "Zlaté Moravce", 260, 60),
    //Banskobystricky kraj
//    BB(County.BB, "Banská Bystrica", 180, 14),
//    BS(County.BB, "Banská Štiavnica", 105, 134),
//    BR(County.BB, "Brezno", 280, 6),
//    DT(County.BB, "Detva", 240, 98),
//    KA(County.BB, "Krupina", 146, 178),
//    LC(County.BB, "Lučenec", 265, 199),
//    PT(County.BB, "Poltár", 308, 146),
//    RA(County.BB, "Revúca", 400, 57),
//    RS(County.BB, "Rimavská Sobota", 400, 186),
//    VK(County.BB, "Veľký Krtíš", 184, 235),
//    ZV(County.BB, "Zvolen", 160, 80),
//    ZC(County.BB, "Žarnovica", 30, 120),
//    ZH(County.BB, "Žiar nad Hronom", 80, 59);

	private County county;
	
	private String districtName;
	
	private int x;
	
	private int y;
	
	private double latitude;
	
	private double longitude;
		
	private District(County county, String districtName, int x, int y, double latitude, double longitude) {
		this.county = county;
		this.districtName = districtName;
		this.x = x;
		this.y = y;
		this.latitude = latitude;
		this.longitude = longitude;
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
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
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

	public static List<District> getAllDistricts() {
		return Arrays.asList(District.values());
	}
	
	public static District getDistrictByDistrictName(String districtName) {

		for(District district : District.values()) {
			if(district.getDistrictName().equals(districtName)) {
				LogUtil.appendLog("#getDistrictByDistrictName(equals): '" + districtName + "' as been resolved as " + district.getDistrictName() + " in county " + district.getCounty().getCountyName());
				return district;
			}
		}

		for(District district : District.values()) {
			if(district.getDistrictName().startsWith(districtName)
					|| ((replaceSpecialChars(district.getDistrictName())).startsWith(districtName))) {
				LogUtil.appendLog("#getDistrictByDistrictName(startsWith): '" + districtName + "' as been resolved as " + district.getDistrictName() + " in county " + district.getCounty().getCountyName());
				return district;
			}
		}
		LogUtil.appendLog("#getDistrictByDistrictName(): '" + districtName + "' has not been found");
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
