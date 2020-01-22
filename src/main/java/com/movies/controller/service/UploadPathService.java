package com.movies.controller.service;

import java.io.File;

public interface UploadPathService {
	File getFilePath(String modifiedFileName, String path);
	boolean deleteFileExists(String accountImageName, String string);
}
