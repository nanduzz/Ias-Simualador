package src;

public class CtrlUni {
	
	public static void fetch(){
		long bus;
		RegsFlags.setReg(RegsFlags.MAR, RegsFlags.getReg(RegsFlags.MAR));
		bus = Mem.readMEM(RegsFlags.MAR);
		RegsFlags.setReg(RegsFlags.MBR, bus);
	}
	
	public static void DecodeL(){
		long mbr = RegsFlags.getReg(RegsFlags.MBR);
		RegsFlags.setReg(RegsFlags.IBR, Util.getINSTR(mbr));
		RegsFlags.setReg(RegsFlags.IR, Util.getOPL(mbr));
		RegsFlags.setReg(RegsFlags.MAR, Util.getADL(mbr));
	}
	
	public static void DecodeR(int r){
		long reg = RegsFlags.getReg(r);
		RegsFlags.setReg(RegsFlags.IR,  Util.getOPR(reg));
		RegsFlags.setReg(RegsFlags.MAR,  Util.getADR(reg));
		RegsFlags.setReg(RegsFlags.PC, RegsFlags.getReg(RegsFlags.PC) + 1);
	}
	
}
