package com.article.article.service.file;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.article.article.exception.BadRequestException;

/**
 *
 * @author Janderson
 */
@Service
public class FileServiceImpl implements FileService {

//	private static final String PATH_FILE = File.separator + "articles";
	private final URL PATH_FILE = this.getClass().getClassLoader().getResource("article");

	@Override
	public String save(MultipartFile multipartfile) {
		try {
			final String filename = String.valueOf(new Random().nextLong()) + multipartfile.getOriginalFilename();
			final Path directory = createDirectory();
			File file = new File(directory.toFile(), filename);
			multipartfile.transferTo(file);
			file.createNewFile();
			return filename;
		} catch (IOException | IllegalStateException ex) {
			throw new BadRequestException("Erro ao fazer upload de arquivo");
		}
	}

	private Path createDirectory() throws IOException {
		Path directory = Paths.get("C:\\artigos");
		if (!Files.exists(directory)) {
			Files.createDirectory(directory);
		}
		return directory;
	}

	@Override
	public File load(String fileName) {
//		final String filename = PATH_FILE.getPath() + new Random().nextLong();
		File file;
		try {
			file = new File(createDirectory().toFile(), fileName);
		} catch (IOException ex) {
			throw new BadRequestException("Erro ao fazer download de arquivo");
		}
		return file;
	}
}
