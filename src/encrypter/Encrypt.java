package encrypter;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;

public class Encrypt {

	private static StringBuilder writeToFile = new StringBuilder();
	private static char[][] seed = new char[128][2];

	private static boolean inSeed(char[] letterSequence) { // Make sure the generated two-character replacement for a
															// single character does not already represent another
															// character.

		for (int x = 0; x < 128; x++)
			if (Arrays.equals(letterSequence, seed[x]))
				return true;
		return false;

	}

	private static void generateSeed() { // Generate a seed for encryption.
		char y1 = 0;
		char y2 = 0;

		char[] seedPopulateAttempt = new char[2];
		
		for (int xIndex = 0; xIndex < 128; xIndex++) {
			do {
				for(int yIndex = 0; yIndex < 2; yIndex++)
					seedPopulateAttempt[yIndex] = (char)(Math.random()*127);
				}while(inSeed(seedPopulateAttempt));
			
			y1 = seedPopulateAttempt[0];
			y2 = seedPopulateAttempt[1];
			seed[xIndex][0] = y1;
			seed[xIndex][1] = y2;
			}
	}

	private static void constructFile(File inFile) {
		
		int offsetSeed = (int) (Math.random() * 128);
		
		generateSeed();
		
		StringBuilder origFile = new StringBuilder();
		String temp = null;

		int currentCharId = 0;

		try {
			Scanner input = new Scanner(inFile);
			input.useDelimiter("");
			while (input.hasNext()) {
				origFile.append(input.next());
			}

			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		writeToFile.append("#encrypted ");
		writeToFile.append((char) offsetSeed);

		for (int xIndex = 0; xIndex < 128; xIndex++) {
			temp = new String(seed[xIndex]);
			writeToFile.append(temp);
		}

		for (int posInFile = 0; posInFile < origFile.length(); posInFile++) {
			currentCharId = (int) origFile.charAt(posInFile);
			if ((currentCharId + offsetSeed) >= 128) 
				temp = new String(seed[currentCharId + offsetSeed - 128]); // MAY NEED TWEAKING
			else 
				temp = new String(seed[currentCharId + offsetSeed]);
			writeToFile.append(temp);
		}

	}

	public static void write(File outFile) {
		
		constructFile(outFile);
		
		try {
			PrintWriter writer = new PrintWriter(outFile);

			writer.print(writeToFile.toString());

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Clear the stringbuilder for subsequent files.
		writeToFile.delete(0, writeToFile.length());
	}

}
