package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Decodificador {
	
	private String[] linhas;
	public List<Pair> palavrasEspeciais = new ArrayList<Decodificador.Pair>();
	public boolean modoDados = false;
	public boolean instL = false;
	public static long Instrucao;
	public static int MemPos;
	private static final int PAD_LIMIT = 8192;

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
		return linha == null || linha.trim().equals("");
	}
	
	public boolean isPalavraEspacial(String linha){
		return linha.contains(":") && !linha.contains("(");
	}
	
	public void codificaArquivo(String filePath){
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
	
	private void setMemPosOrg(String linha){
		MemPos = Integer.valueOf(linha.replaceAll("[^0-9]", ""));
	}
	public void codifica(){
		MemPos = 0;
		for(String linha : this.linhas){
			if(!linhaVazia(linha)){
				if(hasOrg(linha)){
					setMemPosOrg(linha);
					continue;
				}else if(isPalavraEspacial(linha)){
					System.out.println("palavra especial");
					palavrasEspeciais.add(new Pair(MemPos, linha.trim()));
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
	}
	
	public void insereDados(String dados){
		if(instL)
			MemPos++;
		Long l = Long.valueOf(dados.trim());
		if(l < 0){
			dados = Long.toString((Long.valueOf("8000000000", 16) | -l));
		}
		Mem.writeMEM(MemPos, Long.valueOf(dados.trim()), 0l);
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
		}else if(op.contains("STOR")){
			opH = Long.valueOf("10010", 2);
		}else if(op.contains("STOR")){
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
			Mem.writeMEM(MemPos, Instrucao, 0);
		}else{
			Instrucao = Instrucao | inst; 
			instL = false;
			Mem.writeMEM(MemPos, Instrucao, 0);
			Instrucao = 0;
			MemPos++;
		}
	}
	
	public void comeca(String filePath){
		codificaArquivo(filePath);
		codifica();
	}
	public static void main(String[] args) {
		Decodificador d = new Decodificador();
		Scanner scan = new Scanner(System.in);
		String s;
//		do{
//			s = scan.nextLine();
//			
//			System.out.println(d.instrucaoToHex(s).toString());
//			//System.out.println(d.posMemToHex(s).toString());
//		}while(!s.equals("sair"));
		d.comeca("bublesort2.ias");
		Util.printfMEM(200, 0, 200);
		d.escreveArquivo();
	}
	
	public void escreveArquivo(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\teste.txt"));
			String linha = "";
			for(Long l : Mem.mem){
				bw.append(leftPad(Long.toString(l, 16), 10, '0'));
				//bw.append(Long.toString(l, 16));
				bw.newLine();
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String padding(int repeat, char padChar)
			throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException(
					"Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}
	
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, (char)padChar);
		}
		return padding(pads, padChar).concat(str);
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
