package com.article.article.service.file;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Janderson
 */
public interface FileService {

	public String save(MultipartFile multipartfile);

	public File load(String fileName);

}
