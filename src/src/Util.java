package src;

public class Util {
	
	public static int type[];
	
	public static boolean dataTransfer(int ir){
		return (ir == 10 ) || ( ir == 9 ) || ( ir == 33 )
				           || (( ir >= 1 )&& ( ir<=4 ) );  
	}
//teste	
	public static boolean uncondBranch(int ir){
		return ( ( ir == 13 ) || ( ir == 14) );
	}
	
	public static boolean condBranch(int ir){
		return ( ( ir == 15 ) || ( ir == 16 ) );
	}
	
	public static boolean arithmetic (int ir){
		return ( ( ir >= 5 ) && ( ir<=8 ) ) || ( ir == 11 ) || ( ir == 12)
				|| ( ir == 20 ) || ( ir == 21 );
	}
	
	public static boolean addressModify(int ir){
		return ( ( ir == 18 ) || (ir == 19) );
	}
	
	public static void instType(int ir){
		int t = -1;
		if(dataTransfer(ir)){
			t = 0;
		}else if ( uncondBranch(ir) ){
			t = 1;
		}else if ( condBranch(ir) ){
			t = 2;
		}else if ( arithmetic(ir) ){
			t = 3;
		}else if ( addressModify(ir) ){
			t = 4;
		}
		
		if(t > -1){
			type[t] = type[t] + 1;
		}
	}
	
	public static long getOPL(long word){
		return (word & Base.OPL) >> 32;
	}
	
	public static long getOPR(long word){
		return (word & Base.OPR) >> 12;
	}
	
	public static long getADL(long word){
		return (word & Base.ADL) >> 20;
	}
	
	public static long getADR(long word){
		return (word & Base.ADR);
	}
	
	public static long getINSTR(long word){
		return (word & Base.INSTR);
	}
	
	public static long getMAG(long word){
		return (word & Base.MAG) >> 39;
	}
	
	public static long getNUM(long value){
		return (Base.NUM & value);
	}
	
	public static long toComp2(long word){
		long num = getNUM(word);
		if(getMAG(word) == 1){
			num = -num;
		}
		return num;
	}
	public static long toMag(long num){
		long word;
		if(num < 0){
			word = -num;
			word = getNUM(word) | Base.MAG;
		}else{
			word = getNUM(num);
		}
		return word;
	}
	
	public static void printfDelim(){
		System.out.println("-------------------------------------------");
	}
	
	public static void printfInstTypesQtts(){
		String labels[] = {"Data Transfer", "Unconditional Branch", "Conditional Branch", "Arithmetic",
				"Address Modify"};
		int i;
		System.out.println("Quantidade de instruções executadas");
		for(i = 0; i < 5; i++){
			System.out.println(labels[i] + ": " + type[i]);
		}
		printfDelim();
	}
	
	public static void printfMEM(int max, int b, int e){
		int i;
		int mo = 0;
		int mf = max;
		
		if(e<max){
			mf = e;
		}
		System.out.println("Memory(" + mo + " : " + mf + " )");
		for(i = mo; i <= mf; i++){
			//TODO Entender linha 121 do GIT para colocar aqui em baixo
			System.out.println("     " + i + ": " + Mem.readMEM(i));
		}
		printfDelim();
	}
	
	public static void main(String[] args) {
		System.out.println(Base.LCLEAN);
	}
	
	public static void printPC(){
		System.out.println("PC : " + RegsFlags.getReg(RegsFlags.PC));
	}
	
	public static String printONOFF(boolean on){
		if(on){
			return "ON";
		}
		return "OFF";
	}
	
	public static void printREGS(){
		int i;
		String labels[] = {"AC", "IBR", "IR", "MAR", "MBR", "MQ", "PC", "MSK"};
		System.out.println("Registradores");
		for(i = 0; i < labels.length; i++){
			System.out.println("       " + labels[i] + " : " + RegsFlags.getReg(i));
		}
		String fLabels[] = {"FETCH_FLAG", "JMPR_FLAG", "END_FLAG", "READMEM_FLAG", "WRITEMEM_FLAG"};
		for(i = 0; i < fLabels.length; i++){
			System.out.println("      " + fLabels[i] +": " + RegsFlags.isON(i));
		}
		printfDelim();
	}
	
	//TODO fazer essa funcao =)
	public static void loqeMEM(){
		
	}
	// TODO fazer essa funcao
	public static void printHelp(){
		
	}
	
	//TODO terminar essa funcao
	public static void cpuStatus(int max, int ma, int mb){
		char c;
		printPC();
		System.out.print(" > ");
		
		
	}
}
