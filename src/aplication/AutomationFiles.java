package aplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AutomationFiles {
	
	public static void scanFiles(File pasta) throws IOException {
		
		File fileFrom = new File("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom.log"); //modificar caminho após finalizar \\home\\valcann\\backupsFrom.log
		FileWriter writeFile = new FileWriter(fileFrom);
		writeFile.write("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO\n");
		writeFile.write("----------------------------------------------------------\n");
		for (File file : pasta.listFiles()) {
			if (!file.isDirectory()) {
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				String patternCreation = "yyyy/MM/dd";
			    DateFormat formatCreation = new SimpleDateFormat(patternCreation);
			    String formattedCreation = formatCreation.format( new Date( time.toMillis()));
			    String patternModification = "yyyy/MM/dd";
			    SimpleDateFormat formatModification = new SimpleDateFormat(patternModification);   
			    String formattedModification = formatModification.format(new Date(file.lastModified()));
			    String info = (file.getName() + ", " + file.length() + ", " + formattedCreation + ", " + formattedModification + "\n");
			    writeFile.write(info);
			    System.out.println(file.getName() + ", " + file.length() + ", " + formattedCreation + ", " + formattedModification);
			} else {
				scanFiles(file);
			}
		}
		writeFile.close();
	}
	
	public static void removeFiles(File pasta) throws IOException, ParseException {
		
		DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formattedCurrent = currentDate.format(LocalDateTime.now());
		Date convertCurrentDate = (Date) currentDate.parse(formattedCurrent);
        
        //Date convertCurrentDate = formatCreation.parse(formattedCreation);
		for (File file : pasta.listFiles()) {
			if (!file.isDirectory()) {
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				String patternCreation = "yyyy/MM/dd";
				SimpleDateFormat formatCreation = new SimpleDateFormat(patternCreation);
				String formattedCreation = formatCreation.format( new Date( time.toMillis()));
				Date convertDate = formatCreation.parse(formattedCreation);
				if(convertCurrentDate.compareTo(convertDate) > 0) {
					System.out.println("Este arquivo tem a data atual");
				}else {
					System.out.println("Este arqvuio não tem a data atual");
				}
			}else {
				scanFiles(file);
			}
		}
	  }
	public static void main(String[] args) throws IOException, ParseException {
		
		File folder = new File("C:\\Users\\Diego Lins\\Desktop\\valcan"); //modificar caminho após finalizar \\home\\valcann\\backupsFrom
		System.out.println("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO");
		System.out.println("----------------------------------------------------------\n");
		scanFiles(folder);
		removeFiles(folder);
	}
}
