package com.shanghai.volunteer.net.util;

//�ļ���
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//���߰�
//dom4j��

/**
 * @author wang
 */
public class XMLUtil {

	/**
	 * doc2String ��xml�ĵ�����תΪString
	 * 
	 * @return �ַ���
	 * @param document
	 */
	public static String DocumentToString(Document document) {
		String s = "";
		try {
			// ʹ�������������ת��
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// ʹ��GB2312����
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
	 * string2Document ���ַ���תΪDocument
	 * 
	 * @return
	 * @param s
	 *            xml��ʽ���ַ���
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
	 * doc2XmlFile ��Document���󱣴�Ϊһ��xml�ļ�������
	 * 
	 * @return true:����ɹ� flase:ʧ��
	 * @param filename
	 *            ������ļ���
	 * @param document
	 *            ��Ҫ�����document����
	 */
	public static boolean DocumentToXmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/* ��document�е�����д���ļ��� */
			// Ĭ��ΪUTF-8��ʽ��ָ��Ϊ"GB2312"
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
	 * string2XmlFile ��xml��ʽ���ַ�������Ϊ�����ļ�������ַ�����ʽ������xml�����򷵻�ʧ��
	 * 
	 * @return true:����ɹ� flase:ʧ��
	 * @param filename
	 *            ������ļ���
	 * @param str
	 *            ��Ҫ������ַ���
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
	 * DocumentLoad ����һ��xml�ĵ�
	 * 
	 * @return �ɹ�����Document����ʧ�ܷ���null
	 * @param uri
	 *            �ļ�·��
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
	 * BuildBOXXml ����BOXXML�����
	 * 
	 * @return �ɹ�����string����
	 * @param xmlcmd 
	 * 				����
	 * @param content 
	 * 				����
	 */
	public static String BuildBOXXml(String xmlcmd, String content) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<BOD_XML><XML_CMD>" + xmlcmd + "</XML_CMD><DATA_AREA>" + content + "</DATA_AREA></BOD_XML>";
	}
	
	/**
	 * GetDataFromBOD ����BOD�е����ݲ���
	 * 
	 * @return �ɹ�����string����
	 * @param bodxml
	 * 	����
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
