package src;

public class CtrlUni {
	
	public static void fetch(){
		long bus = 0;
		RegsFlags.setReg(RegsFlags.MAR, RegsFlags.getReg(RegsFlags.PC));
		bus = Mem.readMEM(RegsFlags.getReg(RegsFlags.MAR));
		RegsFlags.setReg(RegsFlags.MBR, bus);
	}
	
	public static void getOperands(long mar){
	    if(RegsFlags.isON(RegsFlags.READMEM_FLAG)){  
	    	RegsFlags.setReg(RegsFlags.MBR, Mem.readMEM(mar));
	    	RegsFlags.turnOFF(RegsFlags.READMEM_FLAG);
	    }        
	}
	
	public static void decodeL(){
		long mbr = RegsFlags.getReg(RegsFlags.MBR);
		RegsFlags.setReg(RegsFlags.IBR, Util.getINSTR(mbr));
		RegsFlags.setReg(RegsFlags.IR, Util.getOPL(mbr));
		RegsFlags.setReg(RegsFlags.MAR, Util.getADL(mbr));
	}
	
	public static void decodeR(int r){
		long reg = RegsFlags.getReg(r);
		RegsFlags.setReg(RegsFlags.IR,  Util.getOPR(reg));
		RegsFlags.setReg(RegsFlags.MAR,  Util.getADR(reg));
		RegsFlags.setReg(RegsFlags.PC, RegsFlags.getReg(RegsFlags.PC) + 1);
	}
	
	public static int decodeOp(long ir){
	    int op = 0;
	    if(ir == 1){
	        op = Base.MBR_AC;      
	    }else if(ir == 2){
	        op = Base.N_MBR_AC;      
	    }else if(ir == 3){
	        op = Base.A_MBR_AC;      
	    }else if(ir == 4){
	    	op = 4; 
	    }else if(ir == 33){
	        op = Base.AC_MBR;
	    }else if(ir == 18){
	        op = Base.AC_MBR_L;
	    }else if(ir == 19){
	        op = Base.AC_MBR_R;
	    }else if(ir == 5){
	        op = Base.ADD;
	    }else if(ir == 6){
	        op = Base.SUB;
	    }else if(ir == 7){
	        op = Base.A_ADD;
	    }else if(ir == 8){
	        op = Base.A_SUB;
	    }else if(ir == 10){
	        op = Base.MQ_AC;
	    }else if(ir == 9){
	        op = Base.MBR_MQ;
	    }else if(ir == 11){
	        op = Base.MUL;        
	    }else if(ir == 12){
	        op = Base.DIV;
	    }else if(ir == 20){
	        op = Base.LSH;
	    }else if(ir == 21){
	        op = Base.RSH;
	    }else if((ir == 13)){
	        op = 13;
	    }else if(ir == 14){
	        op = 14;
	    }else if(ir == 15){
	        op = 13;
	    }else if(ir == 16){
	        op = 13;
	    }
	    return op;
	}
	
	public static void memoryAcessControl(long ir){
	    if ((ir != 10)&&(ir>=1)&&(ir<=12)){
	    	RegsFlags.turnON(RegsFlags.READMEM_FLAG);
	    }else if (ir == 33){
	    	RegsFlags.turnON(RegsFlags.WRITEMEM_FLAG);
	    	RegsFlags.setReg(RegsFlags.MSK, 0);
	    }else if (ir == 18){
	    	RegsFlags.turnON(RegsFlags.WRITEMEM_FLAG);
	    	RegsFlags.setReg(RegsFlags.MSK, Base.LCLEAN);
	    }else if (ir == 19){
	    	RegsFlags.turnON(RegsFlags.WRITEMEM_FLAG);
	    	RegsFlags.setReg(RegsFlags.MSK, Base.RCLEAN);
	    }
	}
	

	
	public static void execute(long ir){
		int op = decodeOp(ir);
		Alu.alu(
				op, 
				RegsFlags.getReg(RegsFlags.AC),
				RegsFlags.getReg(RegsFlags.MBR),
				RegsFlags.getReg(RegsFlags.MQ)
				);
	}
	
	public static void branchControl(long ir){	
		if(ir == 0){
			RegsFlags.turnON(RegsFlags.END_FLAG);
		}else if ((ir == 13) || (ir == 14)){
			RegsFlags.turnON(RegsFlags.FETCH_FLAG);
			if(ir == 14){
				RegsFlags.turnON(RegsFlags.JMPR_FLAG);
			}
			RegsFlags.setReg(RegsFlags.PC, RegsFlags.getReg(RegsFlags.MAR));
		}else if ( ir == 15 || ir == 16){
			if(RegsFlags.getReg(RegsFlags.MBR) == 0){
				RegsFlags.turnON(RegsFlags.FETCH_FLAG);
				if( ir == 16){
					RegsFlags.turnON(RegsFlags.JMPR_FLAG);
				}
				RegsFlags.setReg(RegsFlags.PC, RegsFlags.getReg(RegsFlags.MAR));
			}
		}
	}
	
	public static void saveResults(long mar, long mbr, long mask){
		if(RegsFlags.isON(RegsFlags.WRITEMEM_FLAG)){
			Mem.escreveMemoria(mar, mbr, mask);
			RegsFlags.turnOFF(RegsFlags.WRITEMEM_FLAG);
		}
	}
	
	public static void fetchCycle(){
		if(RegsFlags.isON(RegsFlags.FETCH_FLAG)){
			fetch();
			if(RegsFlags.isOFF(RegsFlags.JMPR_FLAG)){
				decodeL();
				RegsFlags.turnOFF(RegsFlags.FETCH_FLAG);
			}else{
				decodeR(RegsFlags.MBR);
				RegsFlags.turnOFF(RegsFlags.JMPR_FLAG);
			}
		}else{
			decodeR(RegsFlags.IBR);
			RegsFlags.turnON(RegsFlags.FETCH_FLAG);
		}
	}
	
	public static long instCycle(){
		fetchCycle();
		memoryAcessControl(RegsFlags.getReg(RegsFlags.IR));
		getOperands(RegsFlags.getReg(RegsFlags.MAR));
		execute(RegsFlags.getReg(RegsFlags.IR));
		branchControl(RegsFlags.getReg(RegsFlags.IR));
		saveResults(
				RegsFlags.getReg(RegsFlags.MAR),
				RegsFlags.getReg(RegsFlags.MBR),
				RegsFlags.getReg(RegsFlags.MSK));
		return RegsFlags.getReg(RegsFlags.IR);
	}

}
