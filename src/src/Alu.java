package src;

//completo
public class Alu {
	
	public static final int HEX = 16;
	
	public static long add(long ac, long mbr){
		long out = Util.toComp2(ac) + Util.toComp2(mbr);
		return Util.toMag(out);
	}
	
	public static long sub(long ac, long mbr){
		long out = Util.toComp2(ac) - Util.toComp2(mbr);
		return Util.toMag(out);
	}
	
	public static long prod(long mbr, long mq){
		long out = Util.toComp2(mbr) * Util.toComp2(mq);
		return Util.toMag(out);
	}
	
	public static long msb(long num){
		long s;
		if(num < 0){
			s = (-num) >> 39;
			s = -s;
		}else{
			s = num >> 39;
		}
		return Util.toMag(s);
	}
	
	public static long lsb(long out){
		return Util.toMag(out);
	}
	
	public static long quoc(long ac, long mbr){
		long out = Util.toComp2(ac) / Util.toComp2(mbr);
		return Util.toMag(out);
	}
	
	public static long mod(long ac, long mbr){
		long out = Util.toComp2(ac) % Util.toComp2(mbr);
		return Util.toMag(out);
	}
	
	public static long lsh(long ac){
		long out = 0;
		if(Util.getMAG(ac) == 1){
			out = Util.getNUM(ac) << 1;
			out = Util.getNUM(out) | Base.MAG;
		}else{
			out = ac << 1;
			out = Util.getNUM(out);
		}
		return out;
	}
	
	public static long rsh(long ac){
		long out;
		if(Util.getMAG(ac) == 1){
			out = Util.getNUM(ac) >> 1;
			out = out | Base.MAG;
		}else{
			out = ac >> 1;
		}
		
		return out;
	}
	
	public static long neg(long num){
		long out;
		if(Util.getMAG(num) == 1){
			out = Util.getNUM(num);
		}else{
			out = num | Base.MAG;
		}
		return out;
	}
	
	public static long absolute(long num){
		long out;
		if(Util.getMAG(num) == 1){
			out = Util.getNUM(num);
		}else{
			out = num;
		}
		return out;
	}
	
	public static long negAbs(long num){
		return neg(absolute(num));  // talvez esteja errado pois no git é neg(abs(num))
	}
	
	public static void alu(int op, long ac, long mbr, long mq){
		if(op == Base.MBR_AC){
			RegsFlags.setReg(RegsFlags.AC, mbr);
		}
		else if(op == Base.A_MBR_AC){
			RegsFlags.setReg(RegsFlags.AC, absolute(mbr));
		}
		else if(op == Base.N_MBR_AC){
			RegsFlags.setReg(RegsFlags.AC, neg(mbr));
		}
		else if(op == Base.AC_MBR){
			RegsFlags.setReg(RegsFlags.MBR, ac);
		}
		else if(op == Base.AC_MBR_L){
			RegsFlags.setReg(RegsFlags.MBR, Util.getADR(ac) << 20);
		}
		else if(op == Base.AC_MBR_R){
			RegsFlags.setReg(RegsFlags.MBR, Util.getADR(ac));
		}
		else if(op == Base.ADD){
			RegsFlags.setReg(RegsFlags.AC, add(ac, mbr));
		}
		else if(op == Base.A_ADD){
			RegsFlags.setReg(RegsFlags.AC, add(ac, absolute(mbr)));
		}
		else if(op == Base.SUB){
			RegsFlags.setReg(RegsFlags.AC, sub(ac,mbr));
		}
		else if(op == Base.A_SUB){
			RegsFlags.setReg(RegsFlags.AC, sub(ac, absolute(mbr)));
		}
		else if(op == Base.MQ_AC){
			RegsFlags.setReg(RegsFlags.AC, mq);
		}
		else if(op == Base.MBR_MQ){
			RegsFlags.setReg(RegsFlags.MQ, mbr);
		}
		else if(op == Base.MUL){
			long out = prod(mbr, mq);
			RegsFlags.setReg(RegsFlags.AC, msb(out));
			RegsFlags.setReg(RegsFlags.MQ, lsb(out));
		}
		else if(op == Base.DIV){
			RegsFlags.setReg(RegsFlags.MQ, quoc(ac,mbr));
			RegsFlags.setReg(RegsFlags.AC, mod(ac,mbr));
		}
		else if(op == Base.LSH){
			RegsFlags.setReg(RegsFlags.AC, lsh(ac));
		}
		else if(op == Base.RSH){
			RegsFlags.setReg(RegsFlags.AC, rsh(ac));
		}
		else if(op == Base.AC_MAG){
			RegsFlags.setReg(RegsFlags.MBR, Util.getMAG(ac));
		}
	}
}
