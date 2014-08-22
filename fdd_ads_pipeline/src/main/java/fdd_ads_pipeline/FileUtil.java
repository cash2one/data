package fdd_ads_pipeline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class.getCanonicalName());
	
	public static List<String> readLines(String fileName) {
		File sourceFile = new File(new File("").getAbsolutePath() + File.separator + fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(sourceFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		List<String> result = Lists.newArrayList();
		String line = null;
		do {
			try {
				line = reader.readLine();
				result.add(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (line != null);
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	public void writeLines(List<List<String>> listLists, File file) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(List<String> list : listLists) {
			writeCsvLine(list, writer);
		}
		
		try {
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void writeCsvLine(List<String> columns, Writer writer) {
		String line = Joiner.on(',').join(columns);
		System.out.println(line);
		for (String value : columns) {
			if (value.contains(",")) {
				logger.warn("ERROR: contains comma in the CSV columms: " + line);
				return;
			}
		}
		
		try {
			writer.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
