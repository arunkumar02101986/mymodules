package com.csw.converters;

import java.io.File;
import java.io.IOException;

public class XmltojsonMain {

	public static void main(String[] args) throws Exception {
		try {
			if (args.length!=0 && args.length == 2 && args[0].contains("json") && args[1].contains("xml")) {
				
				ConverterFactory.createXMLJSONConverter().convertJSONtoXML(new File(args[0]), new File(args[1]));
			} else {
				System.out.println("Invalid Path! Please provide the correct Input/Output Path");
				System.exit(0);
			}

		} catch (IOException e) {
			System.out.println("Exception has been thrown as "+e.getMessage());
		}
	}

}
