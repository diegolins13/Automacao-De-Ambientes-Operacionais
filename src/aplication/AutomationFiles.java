package aplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AutomationFiles {

public static void listFiles(File folder) throws IOException {
		
		System.out.println("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO");
		System.out.println("----------------------------------------------------------\n");
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				String patternCreation = "yyyy/MM/dd";
			    DateFormat formatCreation = new SimpleDateFormat(patternCreation);
			    String formattedCreation = formatCreation.format( new Date( time.toMillis()));
			    String patternModification = "yyyy/MM/dd";
			    SimpleDateFormat formatModification = new SimpleDateFormat(patternModification);   
			    String formattedModification = formatModification.format(new Date(file.lastModified()));
			    System.out.println(file.getName() + ", " + file.length() + ", " + formattedCreation + ", " + formattedModification);
			} else {
				listFiles(file);
			}
		}
	}
	
	public static void saveResults(File folder, String saveResult) throws IOException {
		
		File fileFrom = new File(saveResult);
		FileWriter writeFile = new FileWriter(fileFrom);
		writeFile.write("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO\n");
		writeFile.write("----------------------------------------------------------\n");
		for (File file : folder.listFiles()) {
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
			} else {
				saveResults(file, saveResult);
			}
		}
		writeFile.close();
	}		
	
	@SuppressWarnings("resource")
	public static void copyFile(File source, File destination) throws IOException {
		
		if (destination.exists())
		destination.delete();

		    FileChannel sourceChannel = null;
		    FileChannel destinationChannel = null;

		    try {
		        sourceChannel = new FileInputStream(source).getChannel();
		        destinationChannel = new FileOutputStream(destination).getChannel();
		        sourceChannel.transferTo(0, sourceChannel.size(),
		                destinationChannel);
		    } finally {
		        if (sourceChannel != null && sourceChannel.isOpen())
		            sourceChannel.close();
		        if (destinationChannel != null && destinationChannel.isOpen())
		            destinationChannel.close();
		   }
		}
	
	public static void validationRemove(File folder, String copyDestination) throws IOException, ParseException {
		
		File destination = new File (copyDestination);
		Path path = Paths.get(copyDestination);
		DateTimeFormatter formatCurrentDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formattedCurrent = formatCurrentDate.format(LocalDateTime.now());
		LocalDate convertCurrentDate = LocalDate.parse(formattedCurrent, formatCurrentDate);
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();
				Date convertTime = new Date( time.toMillis() );
				SimpleDateFormat formatCreation = new SimpleDateFormat("yyyy/MM/dd");
				String formattedCreation = formatCreation.format(convertTime);
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate convertDate = LocalDate.parse(formattedCreation, format);
				LocalDate startDate = LocalDate.of(convertDate.getYear(), convertDate.getMonthValue(), convertDate.getDayOfMonth());
				LocalDate endDate = LocalDate.of(convertCurrentDate.getYear(), convertCurrentDate.getMonthValue(), convertCurrentDate.getDayOfMonth());
				long days = ChronoUnit.DAYS.between(startDate, endDate);
				File sourceFile = new File(file.getPath());
				File destinationFile = new File(destination + "\\" + file.getName());
				Files.createDirectories(path);
				if(days > 3) {
					file.delete();
				}else {
					copyFile(sourceFile, destinationFile); 
			    }
			}else {
				validationRemove(file, copyDestination);
			}
		}
	  }
	
	public static void main(String[] args) throws IOException, ParseException {
		
		File folder = new File("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom\\");
		String copyDestination = ("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsTo\\");
		String backupFrom = ("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom.log");
		String backupTo = ("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsTo.log");
		
		
		listFiles(folder); // Listar todos arquivos
		saveResults(folder, backupFrom); // Salvar o resultado no arquivo log em um diretório
		validationRemove(folder, copyDestination); // Fazer validação dos arquivos, remove os que tem mais de 3 dias de criado e copia os que nao 
		saveResults(folder, backupTo); // Salvar o resultado no arquivo log em um diretório 
	}
}
