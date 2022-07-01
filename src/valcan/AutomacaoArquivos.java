package valcan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutomacaoArquivos {
	
	public static void varrerArquivosDaPasta(File pasta) throws IOException {
		//String arq = null;
		for (File file : pasta.listFiles()) {
			if (!file.isDirectory()) {
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				String patternCreation = "yyyy/MM/dd";
			    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(patternCreation);
			    String patternModification = "yyyy/MM/dd";
			    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(patternModification);   
			    String formattedDate2 = simpleDateFormat2.format(new Date(file.lastModified()));
			    String formattedDate1 = simpleDateFormat1.format( new Date( time.toMillis()));
			    System.out.println(file.getName() + ", " + file.length() + ", " + formattedDate1 + ", " + formattedDate2);
				//arq = file.getName() + ", " + file.length() + ", " + formattedDate1 + ", " + formattedDate2;
			} else {
				varrerArquivosDaPasta(file);
			}
		}
		//return arq;
	}

	public static void main(String[] args) throws IOException {
		
		File pasta = new File("C:\\Users\\Diego Lins\\Desktop\\valcan");
		System.out.println("     NOME    | " + "TAMANHO | " + "DATA DE CRIA플O | " + "DATA DE MODIFICA플O");
		varrerArquivosDaPasta(pasta);
		/*
		FileWriter arq = new FileWriter("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom.log");
		@SuppressWarnings("resource")
		PrintWriter gravarArq = new PrintWriter(arq);
		String arquivos = varrerArquivosDaPasta(pasta);
		gravarArq.println("     NOME    | " + "TAMANHO | " + "DATA DE CRIA플O | " + "DATA DE MODIFICA플O");
		gravarArq.println(arquivos);*/
	}
}
