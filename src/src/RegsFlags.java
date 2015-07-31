package src;

// Completa
public class RegsFlags {
	public static final int AC  = 0;
	public static final int IBR = 1;
	public static final int IR  = 2;
	public static final int MAR = 3;
	public static final int MBR = 4;
	public static final int MQ  = 5;
	public static final int PC  = 6;
	public static final int MSK = 7;

	public static final int FETCH_FLAG    = 0;
	public static final int JMPR_FLAG     = 1;
	public static final int END_FLAG      = 2;
	public static final int READMEM_FLAG  = 3;
	public static final int WRITEMEM_FLAG = 4;
	
	public static final int ON  = 1;
	public static final int OFF = 0;
	
	public static long regs[] = {0,0,0,0,0,0,0,0};
	public static int flags[] = {1,0,0,0,0};
	
	public static void pcAddl(){
		flags[6] = flags[6] + 1;
	}
	
	public static void turnON(int flag){
		flags[flag] = ON;
	}
	
	public static void turnOFF(int flag){
		flags[flag] = OFF;
	}
	
	public static boolean isON(int flag){
		return (flags[flag] == ON);
	}
	
	public static boolean isOFF(int flag){
		return (flags[flag] == OFF);
	}
	
	public static long getReg(int reg){
		return regs[reg];
	}
	
	public static void setReg(int reg, long value){
		regs[reg] = value;
	}
}
