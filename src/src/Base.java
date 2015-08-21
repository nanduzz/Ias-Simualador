package src;

public class Base {
	
	//transferencia de dados OK
	public static final int MQ_AC     = Integer.parseInt("00001010", 2);
	public static final int MBR_MQ    = Integer.parseInt("00001001", 2);
	public static final int AC_MBR    = Integer.parseInt("00100001", 2);
	public static final int MBR_AC    = Integer.parseInt("00000001", 2);
	public static final int N_MBR_AC  = Integer.parseInt("00000010", 2);     
	public static final int A_MBR_AC  = Integer.parseInt("00000011", 2);
	
	//aritimetica OK CONFIRMADO
	public static final int ADD       = Integer.parseInt("00000101", 2);
	public static final int A_ADD     = Integer.parseInt("00000111", 2);
	public static final int SUB       = Integer.parseInt("00000110", 2);
	public static final int A_SUB     = Integer.parseInt("00001000", 2);
	public static final int MUL       = Integer.parseInt("00001011", 2);
	public static final int DIV       = Integer.parseInt("00001100", 2);
	public static final int LSH       = Integer.parseInt("00010100", 2);
	public static final int RSH       = Integer.parseInt("00010101", 2);
	//modificação de endereco
	public static final int AC_MBR_L  = Integer.parseInt("00010010", 2);
	public static final int AC_MBR_R  = Integer.parseInt("00010011", 2);
	
	public static final int AC_MAG    = 13;
  
	//public static final int AN_MBR_AC = 4; 
	
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
