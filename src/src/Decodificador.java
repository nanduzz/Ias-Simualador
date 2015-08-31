package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class Decodificador {
	
	private String[] linhas;
	public List<Pair> palavrasEspeciais;
	public boolean modoDados = true;
	public boolean instL = false;
	public static long Instrucao;
	public static int MemPos;

	public void armazenaDados(String linha){
		
	}
	public void armazenaInstrucao(String linha){
		
	}
	public boolean hasOrg(String linha){
		if(linha.contains(".org")){
			modoDados = !modoDados;
			return true;
		}
		return false;
	}
	
	public boolean linhaVazia(String linha){
		return linha == "" || linha == null;
	}
	
	public boolean isPalavraEspacial(String linha){
		return linha.contains(":");
	}
	
	public void getLinhasFromFile(String filePath){
		this.linhas = new String[1024];
		Mem.initMEM(this.linhas.length);
		try{
			ClassLoader classLoader = IasSimulador.class.getClassLoader();
			FileReader arq = new FileReader(new File(classLoader.getResource(filePath).getFile()));
			BufferedReader lerArq = new BufferedReader(arq);
			int i = 0;
			String linha;
			do{
				linha = lerArq.readLine();
				this.linhas[i] = linha;
				i++;
			}while(linha != null);
			arq.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void decodifica(){
		MemPos = 0;
		for(String linha : this.linhas){
			if(hasOrg(linha)){
				continue;
			}else if(isPalavraEspacial(linha)){
				palavrasEspeciais.add(new Pair(MemPos + 1, linha.trim()));
				continue;
			}else{
				if(modoDados){
					insereDados(linha);
				}else{
					insereInstrucao(linha);
				}
			}
		}
	}
	
	public void insereDados(String dados){
		if(instL)
			MemPos++;
		Mem.writeMEM(MemPos, Long.valueOf(dados), 0l);
		MemPos++;
	}
	
	public Long instrucaoToHex(String inst){
		inst.trim();
		Long op  = opToHex(inst);
		Long pos = posMemToHex(inst);
		long instr = op << 12;
		instr = instr | pos;
		return instr;
	}
	
	public Long opToHex(String op){
		Long opH = 0l;
		op = op.trim().toUpperCase();
		if(op.contains("LOAD MQ")){
			opH = Long.valueOf("1010", 2);
		}else if(op.contains("LOAD MQ,")){
			opH = Long.valueOf("1001", 2);
		}else if(op.contains("STOR")){
			opH = Long.valueOf("100001", 2);
		}else if(op.contains("LOAD")){
			opH = Long.valueOf("1", 2);
		}else if(op.contains("LOAD -")){
			opH = Long.valueOf("10", 2);
		}else if(op.contains("LOAD |")){
			opH = Long.valueOf("11", 2);
		}else if(op.contains("JUMP")){
			opH = Long.valueOf("1101", 2);
		}else if(op.contains("JUMP M")){
			opH = Long.valueOf("1110", 2);
		}else if(op.contains("JUMP+M")){
			opH = Long.valueOf("1111", 2);
		}else if(op.contains("JUMP+M")){
			opH = Long.valueOf("10000", 2);
		}else if(op.contains("ADD") && !op.contains("|")){
			opH = Long.valueOf("101", 2);
		}else if(op.contains("ADD |")){
			opH = Long.valueOf("111", 2);
		}else if(op.contains("SUB") && !op.contains("|")){
			opH = Long.valueOf("110", 2);
		}else if(op.contains("SUB |")){
			opH = Long.valueOf("1000", 2);
		}else if(op.contains("MUL")){
			opH = Long.valueOf("1011", 2);
		}else if(op.contains("DIV")){
			opH = Long.valueOf("1100", 2);
		}else if(op.contains("LSH")){
			opH = Long.valueOf("10100", 2);
		}else if(op.contains("RSH")){
			opH = Long.valueOf("10101", 2);
		}else if(op.contains("STOR M")){
			opH = Long.valueOf("10010", 2);
		}else if(op.contains("STOR M")){
			opH = Long.valueOf("10011", 2);
		}
		return opH;
	}
	
	public Long posMemToHex(String pos){
		String p[] = new String[2];
		if(pos.contains("(")){
			if(pos.contains(",")){
				p = pos.split(",");
			}else{
				p[0] = pos;
			}
			p[0] = p[0].replaceAll("[^0-9]", "");
			return Long.valueOf(p[0]);
		}
		return 0l;
	}
	
	public void insereInstrucao(String instrucao){
		Long inst = instrucaoToHex(instrucao);
		if(!instL){
			Instrucao = inst << 20;
			instL = true;
		}else{
			Instrucao = Instrucao | inst; 
			instL = false;
			MemPos++;
		}
	}
	
	public static void main(String[] args) {
		Decodificador d = new Decodificador();
		Scanner scan = new Scanner(System.in);
		String s;
		do{
			s = scan.nextLine();
			
			System.out.println(d.instrucaoToHex(s).toString());
			//System.out.println(d.posMemToHex(s).toString());
		}while(s != "sair");
	}
	
	class Pair{
		String palavra;
		int pos;
		public Pair(int pos, String palavra) {
			this.pos = pos;
			this.palavra = palavra;
		}
		public int getPos(){
			return this.pos;
		}
		public void setPos(int pos){
			this.pos = pos;
		}
		public String getPalavra(){
			return this.palavra;
		}
		public void setPalavra(String palavra){
			this.palavra = palavra;
		}
	
	}
}
