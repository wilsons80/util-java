/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wcs80.util;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ebenezer.botelho
 */
public class LoggerToFile {

	public static void logToFile(String message, Level level) {
		// Adicione ao logger desejado
		Logger.getLogger(message).log(level, message);
	}

	public static void logInfo(String message) {
		logToFile(message, Level.INFO);
	}

	public static void logWarning(String message) {
		logToFile(message, Level.WARNING);
	}

	public static void logSevere(String message) {
		logToFile(message, Level.SEVERE);
	}
	
	
	
	
	public static boolean logInfo(String filename, String message) {
		return logToFile(filename, message, Level.INFO);
	}

	public static boolean logWarning(String filename, String message) {
		return logToFile(filename, message, Level.WARNING);
	}

	public static boolean logSevere(String filename, String message) {
		return logToFile(filename, message, Level.SEVERE);
	}

	public static boolean logToFile(String filename, String message, Level level) {
		try {
			// Cria um alimentador de arquivo que adiciona os dados
			Integer limitSizeInBytes = 1024 * 1024 * 5; // 5Mb
			Integer qtdFilesToUse = 5; // Quantidade de arquivos de log para
										// fazer o log rotate
			Boolean append = true; // Quantidade de arquivos de log para fazer o
									// log rotate
			FileHandler handler = new FileHandler(System
					.getProperty("jboss.server.log.dir").concat(File.separator)
					.concat(filename).concat(".log"), limitSizeInBytes,
					qtdFilesToUse, append);

			// Obtendo o nome da classe que invocou este método
			String classname = "TRT10Util:LoggerToFile";
			String methodname = "TRT10Util:LoggerToFile:logInfo";
			StackTraceElement[] stackTraceElements = Thread.currentThread()
					.getStackTrace();
			if (stackTraceElements.length >= 4) {
				classname = stackTraceElements[3].getClassName();
				methodname = classname.concat(":").concat(
						stackTraceElements[3].getMethodName());
			}

			// Adicione ao logger desejado
			Logger logger = Logger.getLogger(classname);
			logger.addHandler(handler);
			logger.log(level, methodname.concat(": ").concat(message));
			logger.removeHandler(handler);
			handler.flush();
			handler.close(); // Fecha o arquivo

			return true;
		} catch (Exception e) {
			System.out.println("Exceção ao registrar log: ".concat(e
					.getMessage()));
			return false;
		}
	}
	
}
