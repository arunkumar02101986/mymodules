package com.csw.converters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Factory class for creating instances of {@link XMLJSONConverterI}.
 */
public final class ConverterFactory {

	private static final String ARRAY = "array";
	private static final String OBJECT = "object";

	/**
	 * You should implement this method having it return your version of
	 * {@link com.csw.converters.XMLJSONConverterI}.
	 *
	 * @return {@link com.csw.converters.XMLJSONConverterI} implementation you
	 *         created.
	 */
	public static final XMLJSONConverterI createXMLJSONConverter() {

		XMLJSONConverterI converterI = new XMLJSONConverterI() {
			@Override
			public void convertJSONtoXML(File json, File xml) throws IOException {
				System.out.println("Converting from json to xml started");
				StringBuilder builder = new StringBuilder();
				try {
					Object obj = new JSONParser().parse(new FileReader(json));
					commonTypeChecker(obj, null, builder);
					System.out.println("Conversion completed");
					createOutputFile(xml, builder);
				} catch (Exception e) {
					System.out.println("Exception thrown as "+e.getMessage()); 
				}
			}

			/*
			 * To create a new output and check if exist override it or create new files to
			 * write the xml
			 */
			private void createOutputFile(File xml, StringBuilder builder) throws IOException {
				System.out.println("Generating output files as " + xml.toString());
				if (!xml.exists()) {
					xml.createNewFile();
				}
				PrintWriter pWriter = new PrintWriter(xml);
				pWriter.write(builder.toString());
				pWriter.close();
			}

			private void commonTypeChecker(Object obj, Object kStr, StringBuilder builder) {
				String type = extracted(obj);
				
				if (type!=null && type.equalsIgnoreCase(OBJECT)) {
					JSONObject jo = (JSONObject) obj;
					createObjectAlone(jo, builder, kStr);
				} else if (type!=null && type.equalsIgnoreCase(ARRAY)) {
					JSONArray objArray = (JSONArray) obj;
					String kstri = (kStr != null) ? kStr.toString() : null;
					createXMLStartTag(ARRAY, kstri, builder);
					for (int i = 0; i < objArray.size(); i++) {
						commonTypeChecker(objArray.get(i), null, builder);
					}
					createXMLEndTag(ARRAY, builder);
				} else {
					createXMLTag(type, kStr, obj, builder);
				}

			}

			private void createObjectAlone(JSONObject jo, StringBuilder builder, Object kStr) {
				if (jo.size() == 0) {
					createXMLTag(OBJECT, null, null, builder);
				} else {
					if (kStr == null)
						createXMLStartTag(OBJECT, null, builder);
					for (Object keyStr : jo.keySet()) {
						Object keyvalue = jo.get(keyStr);
						if (keyvalue != null) {
							String type = extracted(keyvalue);
							if (type.equalsIgnoreCase(OBJECT)) {
								createXMLStartTag(OBJECT, keyStr.toString(), builder);
							}
							commonTypeChecker(keyvalue, keyStr, builder);
							if (type.equalsIgnoreCase(OBJECT)) {
								createXMLEndTag(OBJECT, builder);
							}
						} else {
							createXMLTag(null, keyStr.toString(), null, builder);
						}
					}
					if (kStr == null)
						createXMLEndTag(OBJECT, builder);

				}
			}

			/*
			 * To create the starting XML Tags
			 */
			private void createXMLStartTag(String tag, String string, StringBuilder builder) {
				builder.append("<" + tag);
				if (string != null)
					builder.append(" name=\"" + string + "\"");
				builder.append(">");
			}

			/*
			 * To create the ending XML Tags
			 */
			private void createXMLEndTag(String tag, StringBuilder builder) {
				builder.append("</" + tag + ">");
			}

			/*
			 * To Check the type of the object
			 */
			private String extracted(Object obj) {
				String type = null;
				if (obj instanceof Integer || obj instanceof Long || obj instanceof Double)
					type = "number";
				else if (obj instanceof String)
					type = "string";
				else if (obj instanceof Boolean)
					type = "boolean";
				else if (obj instanceof JSONObject)
					type = OBJECT;
				else if (obj instanceof JSONArray)
					type = ARRAY;
				return type;

			}

			/*
			 * To Check generate tags based on the object like number,boolean,string,array
			 */
			private void createXMLTag(String tag, Object kElement, Object value, StringBuilder builder) {
				builder.append("<").append(tag);
				if (kElement != null) {
					builder.append(" name=").append("\"" + kElement + "\"");
				}
				if ((kElement != null && tag != null) || value != null) {
					builder.append(">");
				}
				if (value != null) {
					builder.append(value);
				}
				if ((kElement != null && tag != null) || value != null) {
					builder.append("</").append(tag).append(">");
				} else {
					builder.append("/>");
				}
			}
		};
		return converterI;
	}

}