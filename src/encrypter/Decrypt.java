package encrypter;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;

public class Decrypt {

	private static char[][] seed = new char[128][2];
	private static int offset = 0;
	private static StringBuilder origFile = new StringBuilder();
	private static StringBuilder writeToFile = new StringBuilder();

	private static void initFile(File inFile) {
		try {

			Scanner reader = new Scanner(inFile);
			reader.useDelimiter("");

			while (reader.hasNext())
				origFile.append(reader.next());

			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		origFile.delete(0, 11);
		offset = (int) origFile.charAt(0);
		origFile.deleteCharAt(0);

	}

	private static void parseSeed() {

		int counter = 0;

		for (int xIndex = 0; xIndex < 128; xIndex++)
			for (int yIndex = 0; yIndex < 2; yIndex++)
				seed[xIndex][yIndex] = origFile.charAt(counter++);
		origFile.delete(0, 256);

		/*
		 * The following code is for testing purposes to make sure the program
		 * reads the seed correctly. for(int x = 0; x < 128; x++) for(int y = 0;
		 * y < 2; y++) System.out.print(seed[x][y]);
		 */

	}

	private static void constructFile() {

		char[] readIn = new char[2];

		for (int posInOrig = 0; posInOrig < origFile.length(); posInOrig+=2) {
			readIn[0] = origFile.charAt(posInOrig);
			readIn[1] = origFile.charAt(posInOrig+1);

			for (int seedIndex = 0; seedIndex < 128; seedIndex++) {

				if (Arrays.equals(readIn, seed[seedIndex])) {
					if (seedIndex - offset < 0)
						writeToFile.append((char) (seedIndex - offset + 128));
					else
						writeToFile.append((char) (seedIndex - offset));
				}
			}
		}

	}

	public static void write(File outFile) {

		initFile(outFile);
		parseSeed();
		constructFile();

		try {
			PrintWriter writer = new PrintWriter(outFile);
			writer.print(writeToFile.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		origFile.delete(0, origFile.length());         // Clear the stringbuilders for subsequent files.
		writeToFile.delete(0, writeToFile.length());
	}

}
