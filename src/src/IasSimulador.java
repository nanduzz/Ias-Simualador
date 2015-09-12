package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class IasSimulador {
	
	public static void execulta() {
		String[] linhas = new String[1024];
		Mem.initMEM(linhas.length);
		try{
			ClassLoader classLoader = IasSimulador.class.getClassLoader();
			FileReader arq = new FileReader(new File(classLoader.getResource("teste.txt").getFile()));
			BufferedReader lerArq = new BufferedReader(arq);
			int i = 0;
			String linha;
			do{
				linha = lerArq.readLine();
				linhas[i] = linha;
				i++;
			}while(linha != null && i < 1024);
			arq.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(linhas.length > 0){	
			Mem.loadMEM(linhas);
		}
		
		Ccpu.cpu(0, 1023, 0, 1023);
		System.out.println("fim");
	}
}
