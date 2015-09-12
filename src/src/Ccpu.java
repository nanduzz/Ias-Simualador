package src;

public class Ccpu {
	
	public static void cpu(int x, int max, int ma, int mb){
		long ir;
		int line = 0;
		int pc = -1;
		while(RegsFlags.isOFF(RegsFlags.END_FLAG)){
			/*if(x == 1){
				Util.cpuStatus(max, ma,mb);
				System.out.println("x=1");
			}else if (x == 2){
				if(pc == RegsFlags.getReg(RegsFlags.PC)){
					Util.cpuStatus(max, ma, mb);
					line++;
					System.out.println("x=2 dentro do if");
				}
				System.out.println("x=2 dnv");
			}*/
			//Util.printREGS();
			ir = CtrlUni.instCycle();
			Util.instType(ir);
		}
		if(x == 0){
			Util.printfInstTypesQtts();
			Util.printfMEM(max, ma, mb);
			Util.printREGS();
		}
	}
}
