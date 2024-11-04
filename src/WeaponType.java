

public enum WeaponType {
	FIST, GUN, HAZMATSUIT, FLYSWAT, BUGREPELLENT;
	
	public String toString() {
		switch(this) {
			case FIST: return "fist";
			case GUN: return "gun";
			case HAZMATSUIT: return "hazmat suit";
			case FLYSWAT: return "flyswat";
			case BUGREPELLENT: return "bug repellent";
		}
		return "n/a";
	}
}
