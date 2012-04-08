package com.hustaty.android.alergia.util;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

import com.hustaty.android.alergia.beans.DistrictStatus;
import com.hustaty.android.alergia.enums.Alergene;
import com.hustaty.android.alergia.enums.Concentration;
import com.hustaty.android.alergia.enums.County;
import com.hustaty.android.alergia.enums.District;
import com.hustaty.android.alergia.enums.Prognosis;

public class XmlUtil {

	private int countyId;
	private int alergeneId;
	private List<DistrictStatus> districtStatusList;

	public XmlUtil(String xml, int alergeneId, int countyId) {
		this.alergeneId = alergeneId;
		this.countyId = countyId;
		Document doc = getDomElement(xml, alergeneId, countyId);
		districtStatusList = processDocument(doc);
	}
	
	
	private Document getDomElement(String xml, int alergeneId, int countyId) {

		Document doc = null;

		xml = xml.replace(" bgr=\"/pelove-spravodajstvo/public/images/icons/bgr.png\"", "")
				.replace("/pelove-spravodajstvo/public/images/icons/", "")
				.replace("<bgr>/pelove-spravodajstvo/public/mapy/ba_kraj.swf</bgr>", "")
				.replace(".gif", "")
				.replace("smile", "")
				.replace("> <", "><")
				.replace("\n", "");
		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

			
			
		} catch (ParserConfigurationException e) {
			Log.e(LOG_TAG, e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e(LOG_TAG, e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(LOG_TAG, e.getMessage());
			return null;
		}
		return doc;
	}


	private List<DistrictStatus> processDocument(Document doc) {
		
		List<DistrictStatus> districtStatusList = new ArrayList<DistrictStatus>();

		List<District> districtList = District.getDistrictsByCountyId(countyId);
	
		NodeList nodeList = doc.getElementsByTagName("info");
		for(int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			NamedNodeMap nodeMap = node.getAttributes();
			
			int x = Integer.parseInt(nodeMap.getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(nodeMap.getNamedItem("y").getNodeValue());

			String concentrationString = "0";
			String prognosisString = "unknown";
			
			NodeList infoNodeList = node.getChildNodes();
			for(int j=0 ; j < infoNodeList.getLength(); j++) {
				Node infoSubNode = infoNodeList.item(j);
				if("koncentracia".equals(infoSubNode.getNodeName())) {
					concentrationString = infoSubNode.getTextContent();
				} else if("prognoza".equals(infoSubNode.getNodeName())) {
					prognosisString = infoSubNode.getTextContent();
				}
			}
			
			County county = County.getCountyByCountyId(this.countyId);

			for(District district : districtList) {
				if(district.getX() == x && district.getY() == y) {
					//TODO
					districtStatusList.add(new DistrictStatus(district,
							Alergene.getAlergeneById(alergeneId),
							Prognosis.getPrognosisByDescription(prognosisString), 
							Concentration.getConcentrationByOrderNumber(Integer.parseInt(concentrationString))));
				}
			}
		}
		
		return districtStatusList;
		
	}
	
	private String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
	
	private String getAttribute(Element item, String str) {
		return (item.getAttribute(str) == null ? "" : item.getAttribute(str));
	}

	private final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}


	public int getCountyId() {
		return countyId;
	}


	public int getAlergeneId() {
		return alergeneId;
	}


	public List<DistrictStatus> getDistrictStatusList() {
		return districtStatusList;
	}
	
}
