package src;

public class Util {
	
	public static int type[] = {0,0,0,0,0};
	
	//Transferencia de dados
	public static boolean transferenciaDados(long ir){
		return (ir == 10 ) || ( ir == 9 ) || ( ir == 33 )
				           || (( ir == 1 ) || ( ir == 4 ) );  
	}
	//Salto incondicional	
	public static boolean saltoIncondicional(long ir){
		return ( ( ir == 13 ) || ( ir == 14) );
	}
	//salto condicional
	public static boolean saltoCondicional(long ir){
		return ( ( ir == 15 ) || ( ir == 16 ) );
	}
	//aritimetica
	public static boolean aritimetica(long ir){
		return ( ( ir >= 5 ) && ( ir <= 8 ) ) || ( ir == 11 ) || ( ir == 12)
				|| ( ir == 20 ) || ( ir == 21 );
	}
	
	//doficicação de endereco
	public static boolean modificacaoEndereco(long ir){
		return ( ( ir == 18 ) || (ir == 19) );
	}
	
	public static void instType(long ir){
		int t = -1;
		if(transferenciaDados(ir)){
			t = 0;
		}else if ( saltoIncondicional(ir) ){
			t = 1;
		}else if ( saltoCondicional(ir) ){
			t = 2;
		}else if ( aritimetica(ir) ){
			t = 3;
		}else if ( modificacaoEndereco(ir) ){
			t = 4;
		}
		
		if(t > -1){
			type[t] = type[t] + 1;
		}
	}
	
	public static long getOPL(long palavra){
		return (palavra & Base.OPL) >> 32;
	}
	
	public static long getOPR(long palavra){
		return (palavra & Base.OPR) >> 12;
	}
	
	public static long getADL(long palavra){
		return (palavra & Base.ADL) >> 20;
	}
	
	public static long getADR(long palavra){
		return (palavra & Base.ADR);
	}
	
	public static long getINSTR(long palavra){
		return (palavra & Base.INSTR);
	}
	
	public static long getMAG(long palavra){
		return (palavra & Base.MAG) >> 39;
	}
	
	public static long getNUM(long valor){
		return (Base.NUM & valor);
	}
	
	public static long toComp2(long palavra){
		long num = getNUM(palavra);
		if(getMAG(palavra) == 1){
			num = -num;
		}
		return num;
	}
	public static long toMag(long num){
		long palavra;
		if(num < 0){
			palavra = -num;
			palavra = getNUM(palavra) | Base.MAG;
		}else{
			palavra = getNUM(num);
		}
		return palavra;
	}
	
	public static void imprimeDelimitador(){
		System.out.println("-------------------------------------------");
	}
	
	public static void printfInstTypesQtts(){
		String labels[] = {"Transferencia de dados", "Salto incondicional", "Salto Condicional", "Aritimetica",
				"Modificação de endereço"};
		int i;
		System.out.println("Quantidade de instruções executadas");
		for(i = 0; i < 5; i++){
			System.out.println(labels[i] + ": " + type[i]);
		}
		imprimeDelimitador();
	}
	
	public static void imprimeMemoria(int max, int b, int e){
		int i;
		int mo = 0;
		int mf = max;
		
		if(e<max){
			mf = e;
		}
		System.out.println("Memoria(" + mo + " : " + mf + " )");
		for(i = mo; i <= mf; i++){
			long mem = Mem.readMEM(i);
			if( Util.getMAG( mem ) == 1){
				mem = Util.getNUM(mem);
				mem = -mem;
			}
			System.out.println("     " + i + ": " + Long.toString(mem, 10));
		}
		imprimeDelimitador();
	}
	
	public static void printPC(){
		System.out.println("PC : " + RegsFlags.getReg(RegsFlags.PC));
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
			System.out.println("      " + fLabels[i] +": " + (RegsFlags.isON(i) ? "Ligado" : "Desligado") );
		}
		imprimeDelimitador();
	}
}
