package com.joycity.joyclub.we_chat.util;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信支付相关的xml工具
 */
public class WechatXmlUtil {

    /**
     * xml转map
     *
     * @return
     */
    public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(xml)));
            Node root = doc.getElementsByTagName("xml").item(0);
            NodeList nl = root.getChildNodes();
            Node n = null;
            for (int i = 0; i < nl.getLength(); i++) {
                n = nl.item(i);
                if ("#text".equals(n.getNodeName())) {
                    continue;
                }
                params.put(n.getNodeName(), n.getChildNodes().item(0)
                        .getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * inputstreamXml 转map
     */
    public static Map<String, String> xmlToMap(InputStream xmlInput) {
        try {
            String xml = InputStreamToString(xmlInput, "UTF-8");
            return xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map转xml
     *
     * @return
     */
    public static String mapToXml(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : params.keySet()) {
            sb.append("<").append(key).append(">");
            sb.append("<![CDATA[").append(params.get(key)).append("]]>");
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    private static String InputStreamToString(InputStream in, String encoding)
            throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count;
        while ((count = in.read(data, 0, 1024)) != -1) {
            outStream.write(data, 0, count);
        }
        in.close();
        outStream.flush();
        outStream.close();
        return new String(outStream.toByteArray(), encoding);
    }


}
