package encrypter;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

//This is the main class!

public class EncryptDecryptHub {

	public static void main(String[] args) throws Exception { 
		File baseDir = new File(System.getProperty("user.dir")); // Finds current directory
		File[] listOfFiles = baseDir.listFiles();
		
		Scanner reader;

		for(File fileName : listOfFiles) { // This for each loop might not work properly, I haven't tested it.
			System.out.println(fileName);
			if(fileName.getName().endsWith("txt")){ // If the name of the file ends with .txt, open it.
				reader = new Scanner(fileName);

				try {
					if (reader.next().equals("#encrypted")) // If the file is encrypted, decrypt it.
						Decrypt.write(fileName);
					else // Else encrypt the file.
						Encrypt.write(fileName);
					reader.close();
				}catch(NoSuchElementException e){ // In the case the file is empty, catch the NoSuchElement exception.
					System.err.println("One or more of your files are empty!");
				}
			}
		}
	}

}
