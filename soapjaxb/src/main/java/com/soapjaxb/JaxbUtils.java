package com.soapjaxb;

/**
 * D&T: 2020-07-16 02:37
 * Des:
 */
public class JaxbUtils {

    public static String objToXml(GetUserLoginInfoReq obj) {
       /* try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GetUserLoginInfoReq.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8);
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static <T> T xmlToObj(String xml, Class<T> objClass) {
        /*try {
            JAXBContext jaxbContext = JAXBContext.newInstance(objClass);
            Unmarshaller umMarshaller = jaxbContext.createUnmarshaller();
            StringWriter sw = new StringWriter();
            T ret = (T) umMarshaller.unmarshal(new StringReader(xml));
            return ret;
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
