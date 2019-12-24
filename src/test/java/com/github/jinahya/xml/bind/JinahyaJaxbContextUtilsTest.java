package com.github.jinahya.xml.bind;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlType;
import javax.xml.validation.Schema;
import java.io.IOException;

@Slf4j
public class JinahyaJaxbContextUtilsTest {

    // -----------------------------------------------------------------------------------------------------------------
    @XmlType
    public static class DefaultNamespace {

    }

    @XmlType(namespace = "http://some")
    public static class SomeNamespace {

    }

    @XmlType(namespace = "http://other")
    public static class OtherNamespace {

    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void testNewSchemaWithDefaultNamespace() throws JAXBException, IOException, SAXException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(DefaultNamespace.class);
        final Schema schema = JinahyaJaxbContextUtils.newSchema(jaxbContext);
    }

    @Test
    void testNewSchemaWithTwoDifferentNamespaces() throws JAXBException, IOException, SAXException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(SomeNamespace.class, OtherNamespace.class);
        final Schema schema = JinahyaJaxbContextUtils.newSchema(jaxbContext);
    }
}
