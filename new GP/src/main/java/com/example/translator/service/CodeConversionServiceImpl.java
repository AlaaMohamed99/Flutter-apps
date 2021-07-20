package com.example.translator.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.translator.dto.Constants;
import com.example.translator.dto.FlutterDTO;
import com.example.translator.dto.XmlDTO;
import com.example.translator.dto.KotlinDTO;
import com.example.translator.dto.JavaDTO;
import com.example.translator.dto.DartDTO;
import com.example.translator.dto.JavascriptDTO;
import com.example.translator.dto.TypescriptDTO;

import com.example.translator.dto.IonicDTO;
import com.example.translator.dto.Languages;
import com.example.translator.dto.React_NativeDTO;
import com.example.translator.handlers.FlutterHandler;
import com.example.translator.handlers.XmlHandler;
import com.example.translator.handlers.JavaHandler;
import com.example.translator.handlers.KotlinHandler;
import com.example.translator.handlers.DartHandler;
import com.example.translator.handlers.TypescriptHandler;
import com.example.translator.handlers.JavascriptHandler;

import com.example.translator.handlers.IonicHandler;
import com.example.translator.handlers.React_NativeHandler;
import com.example.translator.utils.FileUtils;


@Component
public class CodeConversionServiceImpl implements CodeConversionService  {

	@Autowired
	Map <String,CodeConverter> converters;
	
	public List <File> convertCode(MultipartFile file, Languages inputLanguage, Languages outputLanguage) throws IOException {
		
		String inLanguage=inputLanguage.getLanguage();
		String outLanguage=outputLanguage.getLanguage();
		
			
		String uploadPath = Constants.UPLOAD_PATH_URL;

		String uploadedFileName = null;
		try {
			uploadedFileName = FileUtils.upload(file, uploadPath);
		} catch (Exception exception) {

		}
        List<File> generateFiles=new ArrayList<>();
        // Ionic to React part
		 if(inLanguage.equals(Languages.IONIC.getLanguage()) && outLanguage.equals(Languages.REACT_NATIVE.getLanguage())) {
			IonicDTO ionicDTO = IonicHandler.prepareFiles(uploadedFileName);
			for (File htmlFile : ionicDTO.getHtmlFiles()) {

				CodeConverter requiredConverter = converters.get("Html" + "2" + outLanguage + "Converter");
				requiredConverter.setFileName(htmlFile.getName().split("\\.")[0].split("\\(")[0] + ".js");
				ParseTree parsetree = requiredConverter
						.convert(ionicDTO.getFolderPath() + File.separator + htmlFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);
			}

			for (File scssFile : ionicDTO.getSccsFiles()) {

				CodeConverter requiredConverter = converters.get("Scss" + "2" + outLanguage + "Converter");
				requiredConverter.setFileName(scssFile.getName().split("\\.")[0].split("\\(")[0] + ".js");
				ParseTree parsetree = requiredConverter
						.convert(ionicDTO.getFolderPath() + File.separator + scssFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);
			}
		 }
	 // Ionic to React part
		 else if(inLanguage.equals(Languages.REACT_NATIVE.getLanguage()) && outLanguage.equals(Languages.IONIC.getLanguage())) {

			React_NativeDTO react_NativeDTO = React_NativeHandler.prepareFiles(uploadedFileName);
			for (File javaScriptFile : react_NativeDTO.getJavascriptFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Html" + "Converter");
				requiredConverter.setFileName(javaScriptFile.getName().split("\\.")[0].split("\\(")[0] + ".html");
				ParseTree parsetree = requiredConverter
						.convert(react_NativeDTO.getFolderPath() + File.separator + javaScriptFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			}
			 // Ionic to React part 
		 }
		 
		// flutter to ionic part
         else if(inLanguage.equals(Languages.FLUTTER.getLanguage()) && outLanguage.equals(Languages.IONIC.getLanguage())) {
			 
			FlutterDTO flutterDTO = FlutterHandler.prepareFiles(uploadedFileName);
			for (File dartFile : flutterDTO.getDartFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Html" + "Converter");
				
				requiredConverter.setFileName(dartFile.getName().split("\\.")[0].split("\\(")[0] + ".html");
				ParseTree parsetree = requiredConverter
						.convert(flutterDTO.getFolderPath() + File.separator + dartFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
			for (File dartFile : flutterDTO.getDartFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Scss" + "Converter");
				
				requiredConverter.setFileName(dartFile.getName().split("\\.")[0].split("\\(")[0] + ".scss");
				ParseTree parsetree = requiredConverter
						.convert(flutterDTO.getFolderPath() + File.separator + dartFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }
		 
			// flutter to ionic parts
		 
		 
		 		// xml to ionic part
         else if(inLanguage.equals(Languages.XML.getLanguage()) && outLanguage.equals(Languages.IONIC.getLanguage())) {

        	 
			 
			XmlDTO XmlDTO = XmlHandler.prepareFiles(uploadedFileName);
			for (File xmlFile : XmlDTO.getxmlFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Html" + "Converter");
				
				requiredConverter.setFileName(xmlFile.getName().split("\\.")[0].split("\\(")[0] + ".html");
				ParseTree parsetree = requiredConverter
						.convert(XmlDTO.getFolderPath() + File.separator + xmlFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
			for (File xmlFile : XmlDTO.getxmlFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Scss" + "Converter");
				
				requiredConverter.setFileName(xmlFile.getName().split("\\.")[0].split("\\(")[0] + ".scss");
				ParseTree parsetree = requiredConverter
						.convert(XmlDTO.getFolderPath() + File.separator + xmlFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }
		 
	 		// Java to typescript part
         else if(inLanguage.equals(Languages.JAVA.getLanguage()) && outLanguage.equals(Languages.TYPESCRIPT.getLanguage())) {

        	 
			 
			JavaDTO JavaDTO = JavaHandler.prepareFiles(uploadedFileName);
			for (File javaFile : JavaDTO.getjavaFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Typescript" + "Converter");
				
				requiredConverter.setFileName(javaFile.getName().split("\\.")[0].split("\\(")[0] + ".ts");
				ParseTree parsetree = requiredConverter
						.convert(JavaDTO.getFolderPath() + File.separator + javaFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }
		 
		// kotlin to typescript part
         else if(inLanguage.equals(Languages.KOTLIN.getLanguage()) && outLanguage.equals(Languages.TYPESCRIPT.getLanguage())) {

        	 
			 
			KotlinDTO KotlinDTO = KotlinHandler.prepareFiles(uploadedFileName);
			for (File kotlinFile : KotlinDTO.getkotlinFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Typescript" + "Converter");
				
				requiredConverter.setFileName(kotlinFile.getName().split("\\.")[0].split("\\(")[0] + ".ts");
				ParseTree parsetree = requiredConverter
						.convert(KotlinDTO.getFolderPath() + File.separator + kotlinFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }
		 
			//  typescript to javascript part
         else if(inLanguage.equals(Languages.TYPESCRIPT.getLanguage()) && outLanguage.equals(Languages.JAVASCRIPT.getLanguage())) {

        	 
			 
			TypescriptDTO TypescriptDTO = TypescriptHandler.prepareFiles(uploadedFileName);
			for (File typescriptFile : TypescriptDTO.gettypescriptFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Javascript" + "Converter");
				
				requiredConverter.setFileName(typescriptFile.getName().split("\\.")[0].split("\\(")[0] + ".js");
				ParseTree parsetree = requiredConverter
						.convert(TypescriptDTO.getFolderPath() + File.separator + typescriptFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }

		 
			// dart to typescript part
         else if(inLanguage.equals(Languages.DART.getLanguage()) && outLanguage.equals(Languages.TYPESCRIPT.getLanguage())) {

        	 
			 
        	 DartDTO DartDTO = DartHandler.prepareFiles(uploadedFileName);
			for (File dartFile : DartDTO.getdartFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Typescript" + "Converter");
				
				requiredConverter.setFileName(dartFile.getName().split("\\.")[0].split("\\(")[0] + ".ts");
				ParseTree parsetree = requiredConverter
						.convert(DartDTO.getFolderPath() + File.separator + dartFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }

		 
		 
			// javascript to typescript part
         else if(inLanguage.equals(Languages.JAVASCRIPT.getLanguage()) && outLanguage.equals(Languages.TYPESCRIPT.getLanguage())) {

        	 
			 
        	 JavascriptDTO JavascriptDTO = JavascriptHandler.prepareFiles(uploadedFileName);
			for (File javascriptFile : JavascriptDTO.getjavascriptFiles()) {
				CodeConverter requiredConverter = converters.get(inLanguage + "2" + "Typescript" + "Converter");
				
				requiredConverter.setFileName(javascriptFile.getName().split("\\.")[0].split("\\(")[0] + ".ts");
				ParseTree parsetree = requiredConverter
						.convert(JavascriptDTO.getFolderPath() + File.separator + javascriptFile.getName());
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk((ParseTreeListener) requiredConverter, parsetree);
				File generatedFile = requiredConverter.getFile();
				generateFiles.add(generatedFile);

			 }	 
		 }





		return generateFiles;
		
		
	}




}
