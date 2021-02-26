package com.infraleap.playground;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

    	if (args.length != 1){
    		throw new RuntimeException("Need file name.");
		}

		Path inputFile = FileSystems.getDefault().getPath(args[0]);

		Stream<String> stringsStream = Files.lines(inputFile);
		String[] strings = stringsStream.toArray(String[]::new);


		List<String> content = new ArrayList<>();
		StringBuilder contentBuilder = new StringBuilder();

		for (int i=0; i<strings.length; /* don't increment here */){
			if (strings[i].length() == 2){

				//contentBuilder.append("(");

				contentBuilder.append(cleanString(strings[i]).toUpperCase()); // country code
				contentBuilder.append(",");

				contentBuilder.append(cleanString(strings[i+2])); // risk level
				//contentBuilder.append(",");

				//contentBuilder.append(cleanString(strings[i])); // country name

				i+=3; // we consumed three strings

				while (i < strings.length && strings[i].length() != 2){ // if it's a 2-letter country code, it belongs to the next for loop iteration
					//contentBuilder.append(strings[i]); /* ignore those comment lines */
					i++;
				}
				//contentBuilder.append(")");

				System.out.println(contentBuilder);

				content.add(contentBuilder.toString());
				contentBuilder = new StringBuilder();
			}
		}

		System.out.println(contentBuilder);

		PrintWriter pw = new PrintWriter(args[0]+ ".csv");
		for (String line : content) {
			pw.println(line);
		}
		pw.close();

		// Now use e.g. UNIX 'sort' and sort by country names, matching the code we have in the project.

    }

    private static String cleanString(String toBeCleaned){
    	return toBeCleaned.replaceAll(",", "").replaceAll(Pattern.quote("("), "").replaceAll(Pattern.quote(")"), "");
	}
}
