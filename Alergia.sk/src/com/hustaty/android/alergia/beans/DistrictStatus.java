package com.hustaty.android.alergia.beans;

import com.hustaty.android.alergia.enums.Alergene;
import com.hustaty.android.alergia.enums.Concentration;
import com.hustaty.android.alergia.enums.District;
import com.hustaty.android.alergia.enums.Prognosis;

public class DistrictStatus {
	
	private District district;
	private Alergene alergene;
	private Prognosis prognosis;
	private Concentration concentration;
	
	public DistrictStatus(District district, Alergene alergene, Prognosis prognosis, Concentration concentration) {
		super();
		this.district = district;
		this.alergene = alergene;
		this.prognosis = prognosis;
		this.concentration = concentration;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Alergene getAlergene() {
		return alergene;
	}

	public void setAlergene(Alergene alergene) {
		this.alergene = alergene;
	}

	public Prognosis getPrognosis() {
		return prognosis;
	}

	public void setPrognosis(Prognosis prognosis) {
		this.prognosis = prognosis;
	}

	public Concentration getConcentration() {
		return concentration;
	}

	public void setConcentration(Concentration concentration) {
		this.concentration = concentration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alergene == null) ? 0 : alergene.hashCode());
		result = prime * result
				+ ((concentration == null) ? 0 : concentration.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((prognosis == null) ? 0 : prognosis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistrictStatus other = (DistrictStatus) obj;
		if (alergene != other.alergene)
			return false;
		if (concentration != other.concentration)
			return false;
		if (district != other.district)
			return false;
		if (prognosis != other.prognosis)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DistrictStatus [district=" + district + ", alergene="
				+ alergene + ", prognosis=" + prognosis + ", concentration="
				+ concentration + "]";
	}
	
	public String toHumanReadableString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Koncentrácia: ");
		sb.append(this.concentration.getDescription() + "\n\n");
		sb.append("Prognóza: " + this.prognosis.getHumanReadableDescription());
		return sb.toString(); 
	}
	

}
