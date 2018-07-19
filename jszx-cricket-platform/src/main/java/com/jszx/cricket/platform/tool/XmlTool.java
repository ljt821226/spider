package com.jszx.cricket.platform.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jszx.cricket.platform.code.SystemCode;

/**
 * 
 * XML工具类：提供xml常用操作方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月20日 下午2:24:17
 *
 */
public class XmlTool {

	private static final Logger logger = LoggerFactory.getLogger(XmlTool.class);

	/*
	 * Schema 的语言定义
	 */
	private final static String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static XmlMapper xmlMapper = new XmlMapper();

	/**
	 * 
	 * 读取xml文件，生成对应的文档对象
	 * 
	 * @param fileName
	 * @param encoding
	 * @param xsdName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @date 2016年10月20日 下午2:01:09
	 */
	public static Document load(String fileName, String encoding, String xsdName) throws Exception {
		Document doc = null;
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		InputSource is = null;
		InputStreamReader isr = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setIgnoringElementContentWhitespace(true);
			dbf.setIgnoringComments(true);
			if (!StringTool.isEmpty(xsdName)) {
				dbf.setSchema(loadSchema(xsdName));
			}
			db = dbf.newDocumentBuilder();
			isr = FileTool.getInputStreamReader(fileName, encoding);
			is = new InputSource(isr);
			doc = db.parse(is);
			db.reset();
			return doc;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (isr != null) {
				isr.close();
			}
		}
	}

	/**
	 * 
	 * 读取xml文件，生成对应的文档对象
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:18:35
	 */
	public static Document load(String fileName, String encoding) throws Exception {
		return load(fileName, encoding, "");
	}

	/**
	 * 
	 * 读取xml文件，生成对应的文档对象
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:18:45
	 */
	public static Document load(String fileName) throws Exception {
		return load(fileName, SystemCode.COMMON.ENCODING.value(), "");
	}

	/**
	 * 
	 * 校验xml文件是否正确，如果有异常则校验失败
	 * 
	 * @param doc
	 * @param xsdName
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年10月20日 下午2:02:45
	 */
	public static void validate(Document doc, String xsdName) throws Exception {
		Schema schema = null;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XML_SCHEMA);
			File schemaFile = new File(xsdName);
			schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			Source source = new DOMSource(doc);
			validator.validate(source);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * 校验xml文件是否正确，如果有异常则校验失败
	 * 
	 * @param xmlName
	 * @param xsdName
	 * @author lv_juntao@uisftech.com
	 * @throws Exception
	 * @date 2016年10月20日 下午2:02:59
	 */
	public static void validate(String xmlName, String xsdName) throws Exception {
		Schema schema = null;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XML_SCHEMA);
			File schemaFile = new File(xsdName);
			schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(xmlName);
			validator.validate(source);
		} catch (SAXException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * 根据xsd文件，生产Schema对象
	 * 
	 * @param xsdName
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws SAXException
	 * @date 2016年10月20日 下午2:03:08
	 */
	private static Schema loadSchema(String xsdName) throws SAXException {
		Schema schema = null;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XML_SCHEMA);
			File schemaFile = new File(xsdName);
			schema = schemaFactory.newSchema(schemaFile);
			return schema;
		} catch (SAXException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * 获取CDATA的内容
	 * 
	 * @param node
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:03:16
	 */
	public static String getCDataText(Node node) {
		CDATASection cdata = (CDATASection) node.getChildNodes().item(1);
		Document factory = node.getOwnerDocument();
		String data = cdata.getData();
		Text newNode = factory.createTextNode(data);
		return newNode.getData().trim();
	}

	/**
	 * 
	 * 根据路径获取节点
	 * 
	 * @param parentNode
	 * @param path
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws XPathExpressionException
	 * @date 2016年10月20日 下午2:03:24
	 */
	public static Element getNodeByPath(Object parentNode, String path) throws XPathExpressionException {
		try {
			Element returnNode = null;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			returnNode = (Element) xpath.evaluate(path, parentNode, XPathConstants.NODE);
			return returnNode;
		} catch (XPathExpressionException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public Element createElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
		return child;
	}

	/**
	 * 
	 * 创建节点
	 * 
	 * @param parent
	 * @param tagName
	 * @param value
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:03:33
	 */
	public Element createElement(Element parent, String tagName, String value) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, value);
		parent.appendChild(child);
		return child;
	}

	/**
	 * 
	 * 设置指定Element的值
	 * 
	 * @param element
	 * @param val
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:03:44
	 */
	public static void setElementValue(Element element, String val) {
		Node node = element.getOwnerDocument().createTextNode(val);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeType() == Node.TEXT_NODE)// 是一个Text Node
			{
				nd.setNodeValue(val);
				return;
			}
		}
		element.appendChild(node);
	}

	/**
	 * 
	 * 获取指定Element的值
	 * 
	 * @param element
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:03:53
	 */
	public static String getElementValue(Element element) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.TEXT_NODE)// 是一个Text Node
			{
				return element.getFirstChild().getNodeValue();
			}
		}
		return null;
	}

	/**
	 * 
	 * 在父结点中查询指定名称的结点集
	 * 
	 * @param parent
	 * @param name
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:04:02
	 */
	public static Element[] getElementsByName(Element parent, String name) {
		ArrayList<Node> resList = new ArrayList<Node>();
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(name)) {
				resList.add(nd);
			}
		}
		Element[] res = new Element[resList.size()];
		for (int i = 0; i < resList.size(); i++) {
			res[0] = (Element) resList.get(i);
		}
		return res;
	}

	/**
	 * 
	 * 获取XML的根结点
	 * 
	 * @param doc
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月20日 下午2:04:11
	 */
	public Element getRoot(Document doc) {
		return doc.getDocumentElement();
	}

	/**
	 * 
	 * 根据DOM生成XML文件
	 * 
	 * @param doc
	 * @param fileName
	 * @param encoding
	 * @author lv_juntao@uisftech.com
	 * @throws TransformerException
	 * @date 2016年10月20日 下午2:04:27
	 */
	public void write(Document doc, String fileName, String encoding) throws TransformerException {
		TransformerFactory tfactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
			if (!StringTool.isEmpty(encoding)) {
				transformer.setOutputProperty("encoding", encoding);
			}
			transformer.transform(source, result);
			transformer.reset();
		} catch (TransformerConfigurationException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (TransformerException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * 转成json格式的对象
	 * 
	 * @param is
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(InputStream is, Class<T> clazz) {
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(is, clazz);
		} catch (Exception e) {
			logger.info("XmlToObject failed:", e);
		}
		return null;
	}

	/**
	 * 
	 * 对象转为xml
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 * @author lv_juntao@uisftech.com
	 * @date 2017年12月22日 下午4:12:22
	 */
	public static String toXml(Object object) throws IOException {
		return xmlMapper.writeValueAsString(object);
	}

}
