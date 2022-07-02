package aplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutomationFiles {
	
	public static void scanFiles(File pasta) throws IOException {
		
		File fileFrom = new File("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom.log"); //modificar caminho após finalizar
		FileWriter writeFile = new FileWriter(fileFrom);
		writeFile.write("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO\n");
		writeFile.write("----------------------------------------------------------\n");
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
			    String info = (file.getName() + ", " + file.length() + ", " + formattedDate1 + ", " + formattedDate2 + "\n");
			    writeFile.write(info);
			    System.out.println(file.getName() + ", " + file.length() + ", " + formattedDate1 + ", " + formattedDate2);
			} else {
				scanFiles(file);
			}
		}
		writeFile.close();
	}

	public static void main(String[] args) throws IOException {
		
		File folder = new File("C:\\Users\\Diego Lins\\Desktop\\valcan"); //modificar caminho após finalizar
		System.out.println("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO");
		System.out.println("----------------------------------------------------------\n");
		scanFiles(folder);
	}
}
