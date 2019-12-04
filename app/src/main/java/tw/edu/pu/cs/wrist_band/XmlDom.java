package tw.edu.pu.cs.wrist_band;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlDom {

    public static String createDom(String voiceName, String textToSynthesize) {
        Document doc = null;
        Element speak, voice;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            if (doc != null) {
                speak = doc.createElement("speak");
                speak.setAttribute("version", "1.0");
                speak.setAttribute("xmlns", "https://www.w3.org/2001/10/synthesis");
                speak.setAttribute("xml:lang", "zh-cn");
                voice = doc.createElement("voice");
//                voice.setAttribute("xml:lang", locale);
//                voice.setAttribute("xml:gender", genderName);
                voice.setAttribute("name", voiceName);
                voice.appendChild(doc.createTextNode(textToSynthesize));
                speak.appendChild(voice);
                doc.appendChild(speak);
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return transformDom(doc);
    }

    private static String transformDom(Document doc) {
        StringWriter writer = new StringWriter();
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
}
