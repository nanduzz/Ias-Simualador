package src;

//completo
public class Mem {
	
	public static long mem[];
	
	public static void initMEM(int size){
		
	}
	public static long readMEM(long pos){
		int p = (int) pos; // gambi
		return mem[p];
	}
	
	public static void writeMEM(long pos, long bus, long mask){
		int p = (int) pos; // <= gambiarra
		mem[p] = (mask & mem[p]) | bus;
	}
}