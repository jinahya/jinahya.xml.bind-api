package com.github.jinahya.xml.bind;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import java.io.IOException;

/**
 * Utilities for {@link SchemaOutputResolver}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class JinahyaSchemaOutputResolverUtils {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An interface to solely re-define the {@link SchemaOutputResolver#createOutput(String, String)} method.
     */
    interface InterfacedSchemaOutputResolver {

        Result createOutput(String namespaceUri, String suggestedFileName) throws IOException;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static SchemaOutputResolver of(final InterfacedSchemaOutputResolver resolver) {
        if (resolver == null) {
            throw new NullPointerException("resolver is null");
        }
        return new SchemaOutputResolver() {
            @Override
            public Result createOutput(final String namespaceUri, final String suggestedFileName) throws IOException {
                return resolver.createOutput(namespaceUri, suggestedFileName);
            }
        };
    }

    // -----------------------------------------------------------------------------------------------------------------

    private JinahyaSchemaOutputResolverUtils() {
        super();
    }
}
