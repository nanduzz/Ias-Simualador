package src;

//completo
public class Mem {
	
	public static long mem[] = new long[1024];
	
	public static void initMEM(int size){
		if(size > 1024){
			System.out.println("Tamanho maximo de memória 1024" );
			size = 1024;
		}
		for(int i = 0; i < size; i++){
			mem[i] = 0;
		}
	}
	public static void loadMEM(String[] linhas){
		int i = 0;
		for (String linha : linhas){
			if(linha != null){
				long valor = Long.parseLong(linha, 16);
				writeMEM(i, valor, 0);
			}
			i++;
		}
	}
	
	public static long readMEM(long pos){
		int p = (int) pos;
		return mem[p];
	}
	
	public static void writeMEM(long pos, long bus, long mask){
		int p = (int) pos;
		mem[p] = (mask & mem[p]) | bus;
	}
}