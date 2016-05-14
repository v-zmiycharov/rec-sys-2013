package utils;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileReader {
	@SuppressWarnings("unchecked")
	public static List<String> getLines(String path) throws Exception {
		return FileUtils.readLines(new File(path));
	}
}
