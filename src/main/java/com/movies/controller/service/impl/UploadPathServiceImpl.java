package com.movies.controller.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.controller.service.UploadPathService;

@Service
public class UploadPathServiceImpl implements UploadPathService {

	@Autowired
	ServletContext context;

	@Override
	public File getFilePath(String modifiedFileName, String path) {
		boolean exists = new File(context.getRealPath("/" + path + "/")).exists();
		if (!exists) {
			new File(context.getRealPath("/" + path + "/")).mkdir();
		}
		String modifiedFilePath = context.getRealPath("/" + path + "/" + File.separator + modifiedFileName);
		File file = new File(modifiedFilePath);
		return file;
	}

	@Override
	public boolean deleteFileExists(String accountImageName, String path) {
		boolean exists = new File(context.getRealPath("/" + path + "/" + File.separator + accountImageName)).exists();
		if(exists) {
			String modifiedFilePath = context.getRealPath("/" + path + "/" + File.separator + accountImageName);
			File file = new File(modifiedFilePath);
			boolean isDeleted = file.delete();
			return isDeleted;
		}
		return exists;
	}

}
