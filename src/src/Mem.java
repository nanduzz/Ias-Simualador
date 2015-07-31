package src;

//completo
public class Mem {
	
	public static long mem[];
	
	public static void initMEM(int size){
		
	}
	public static long readMEM(int pos){
		return mem[pos];
	}
	
	public static void writeMEM(int pos, long bus, long mask){
		mem[pos] = (mask & mem[pos]) | bus;
	}
}