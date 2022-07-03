package aplication;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AutomationFiles {

	public static void scanFiles(File folder) throws IOException {
		
		File fileFrom = new File("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsFrom.log"); //modificar caminho após finalizar \\home\\valcann\\backupsFrom.log
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
			    System.out.println(file.getName() + ", " + file.length() + ", " + formattedCreation + ", " + formattedModification);
			} else {
				scanFiles(file);
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
	
	public static void validationRemove(File folder) throws IOException, ParseException {
		
		String destinationPath = ("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsTo\\");//modificar caminho após finalizar \\home\\valcann\\backupsTo\\
		File destination = new File ("C:\\Users\\Diego Lins\\Desktop\\valcan\\backupsTo\\"); //modificar caminho após finalizar \\home\\valcann\\backupsTo\\
		Path path = Paths.get(destinationPath);
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
				validationRemove(file);
			}
		}
	  }
	
	public static void main(String[] args) throws IOException, ParseException {
		
		File folder = new File("C:\\Users\\Diego Lins\\Desktop\\valcan\\"); //modificar caminho após finalizar \\home\\valcann\\backupsFrom
		System.out.println("   NOME  | " + "TAMANHO | " + "DATA DE CRIAÇÃO | " + "DATA DE MODIFICAÇÃO");
		System.out.println("----------------------------------------------------------\n");
		scanFiles(folder);
		validationRemove(folder);
	}
}
