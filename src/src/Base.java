package src;

public class Base {
	
	public static final int MBR_AC    = 1;
	public static final int AC_MBR    = 2;
	public static final int ADD       = 3;
	public static final int SUB       = 4;
	public static final int MQ_AC     = 5;
	public static final int MBR_MQ    = 6;
	public static final int MUL       = 7;
	public static final int DIV       = 8;
	public static final int LSH       = 9;
	public static final int RSH       = 10;
	public static final int AC_MBR_L  = 11;
	public static final int AC_MBR_R  = 12;
	public static final int AC_MAG    = 13;
	public static final int N_MBR_AC  = 14;      
	public static final int A_MBR_AC  = 15;    
	public static final int AN_MBR_AC = 16; 
	public static final int A_ADD     = 17;
	public static final int A_SUB     = 18;
	
	public static final long LCLEAN = Long.parseLong("ff000fffff", 16);
	public static final long RCLEAN = Long.parseLong("fffffff000", 16);

	public static final long MAG = Long.parseLong("8000000000", 16);
	public static final long NUM = Long.parseLong("7fffffffff", 16);
	public static final long OPL = Long.parseLong("ff00000000",16);
	public static final long OPR = Long.parseLong("ff000", 16);
	public static final long ADL = Long.parseLong("fff00000", 16);
	public static final long ADR = Long.parseLong("fff", 16);
	public static final long INSTR = Long.parseLong("fffff", 16);
}
