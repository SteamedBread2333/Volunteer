package com.shanghai.volunteer.net.util;

//文件包
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//工具包
//dom4j包

/**
 * @author wang
 */
public class XMLUtil {

	/**
	 * doc2String 将xml文档内容转为String
	 * 
	 * @return 字符串
	 * @param document
	 */
	public static String DocumentToString(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用GB2312编码
			OutputFormat format = new OutputFormat("  ", true, "GB2312");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * string2Document 将字符串转为Document
	 * 
	 * @return
	 * @param s
	 *            xml格式的字符串
	 */
	public static Document StringToDocument(String s) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static boolean DocumentToXmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			// 默认为UTF-8格式，指定为"GB2312"
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * string2XmlFile 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param str
	 *            需要保存的字符串
	 */
	public static boolean StringToXmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = DocumentToXmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * DocumentLoad 载入一个xml文档
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param uri
	 *            文件路径
	 */
	public static Document Load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	/**
	 * BuildBOXXml 生成BOXXML传输包
	 * 
	 * @return 成功返回string对象
	 * @param xmlcmd 
	 * 				命令
	 * @param content 
	 * 				内容
	 */
	public static String BuildBOXXml(String xmlcmd, String content) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<BOD_XML><XML_CMD>" + xmlcmd + "</XML_CMD><DATA_AREA>" + content + "</DATA_AREA></BOD_XML>";
	}
	
	/**
	 * GetDataFromBOD 返回BOD中的数据部分
	 * 
	 * @return 成功返回string对象
	 * @param bodxml
	 * 	内容
	 */
	public static String GetDataFromBOD(String bodxml){
		Document xmldoc = StringToDocument(bodxml);
		Element root = xmldoc.getRootElement();
		String dataarea = root.selectSingleNode("//DATA_AREA").asXML();
		//dataarea = dataarea.substring(11);
		//dataarea = dataarea.substring(0,dataarea.length()-12);
		return dataarea;
			
	}
	
}
