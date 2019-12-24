package com.github.jinahya.xml.bind;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A utility class for {@link JAXBContext}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class JinahyaJaxbContextUtils {

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    // https://www.programcreek.com/java-api-examples/?class=javax.xml.bind.JAXBContext&method=generateSchema
    public static Schema newSchema(final JAXBContext jaxbContext,
                                   final Supplier<? extends SchemaFactory> factorySupplier)
            throws IOException, SAXException {
        if (jaxbContext == null) {
            throw new NullPointerException("jaxbContext is null");
        }
        if (factorySupplier == null) {
            throw new NullPointerException("factorySupplier is null");
        }
        final List<ByteArrayOutputStream> outputs = new ArrayList<>();
        jaxbContext.generateSchema(JinahyaSchemaOutputResolverUtils.of((nameSpaceUri, suggestedFileName) -> {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            outputs.add(output);
            final Result result = new StreamResult(output);
            result.setSystemId("");
            return result;
        }));
        assert !outputs.isEmpty() : "empty outputs";
        final Source[] sources
                = outputs.stream()
                .map(ByteArrayOutputStream::toByteArray)
                .map(ByteArrayInputStream::new)
                .map(input -> new StreamSource(input, ""))
                .toArray(StreamSource[]::new);
        return factorySupplier.get().newSchema(sources);
    }

    public static Schema newSchema(final JAXBContext jaxbContext, final String schemaLanguage)
            throws IOException, SAXException {
        if (schemaLanguage == null) {
            throw new NullPointerException("schemaLanguage is null");
        }
        return newSchema(jaxbContext, () -> SchemaFactory.newInstance(schemaLanguage));
    }

    // -----------------------------------------------------------------------------------------------------------------
    private JinahyaJaxbContextUtils() {
        super();
    }
}
