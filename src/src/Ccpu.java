package src;

public class Ccpu {
	
	public static void cpu(int x, int max, int ma, int mb){
		long ir;
		while(RegsFlags.isOFF(RegsFlags.END_FLAG)){
			Util.printREGS();
			ir = CtrlUni.instCycle();
			Util.instType(ir);
		}
		if(x == 0){
			Util.printfInstTypesQtts();
			Util.imprimeMemoria(max, ma, mb);
			Util.printREGS();
		}
	}
}
