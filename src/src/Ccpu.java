package src;

import java.util.Scanner;

public class Ccpu {
	
	public static void cpu(int x, int max, int ma, int mb){
		Scanner scan = new Scanner(System.in);
		char c;
		long ir;
		int line = 0;
		//if(x!= 0)
		//	printHelp();
		int pc = -1; // alterado
		if (x == 2){
			//System.out.print("Stop where PC > ");
			pc = scan.nextInt();
			//TODO falta o getCHAR()
			//System.out.println("zx=2");
		}
		while(RegsFlags.isOFF(RegsFlags.END_FLAG)){
			if(x == 1){
				Util.cpuStatus(max, ma,mb);
				//System.out.println("x=1");
			}else if (x == 2){
				if(pc == RegsFlags.getReg(RegsFlags.PC)){
					Util.cpuStatus(max, ma, mb);
					line++;
					//System.out.println("x=2 dentro do if");
				}
				//System.out.println("x=2 dnv");
			}
			ir = CtrlUni.instCycle(); // cast da gambi
			Util.instType(ir);
			if(line == 2){
				line = 0;
				System.out.print("Stop where pc >");
				pc = scan.nextInt();
				// TODO falta o getCHAR()
				
			}

		}
		if(x == 0){
			Util.printfInstTypesQtts();
			Util.printfMEM(max, ma, mb);
			Util.printREGS();
		}
	}
}
