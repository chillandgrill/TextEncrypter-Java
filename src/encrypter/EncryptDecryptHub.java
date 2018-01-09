package encrypter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

public class EncryptDecryptHub {

	public static void main(String[] args) throws Exception { 
		File baseDir = new File(System.getProperty("user.dir"));
		File[] listOfFiles = baseDir.listFiles();
		
		Scanner reader;

		for(File fileName : listOfFiles) {
			System.out.println(fileName);
			if(fileName.getName().endsWith("txt")){
				reader = new Scanner(fileName);

				try {
					if (reader.next().equals("#encrypted"))
						Decrypt.write(fileName);
					else
						Encrypt.write(fileName);
					reader.close();
				}catch(NoSuchElementException e){
					System.err.println("One or more of your files are empty!");
				}
			}
		}
	}

}
