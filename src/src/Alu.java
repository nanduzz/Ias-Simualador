package src;

public class Alu {
	
	public int add(int ac, int mbr){
		int out = toComp2(ac) + toComp2(mbr);
		return toMag(out);
	}
}
