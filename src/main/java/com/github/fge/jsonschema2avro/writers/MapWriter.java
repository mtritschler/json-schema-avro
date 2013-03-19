package com.github.fge.jsonschema2avro.writers;

import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.jsonschema.util.ValueHolder;
import com.github.fge.jsonschema2avro.AvroWriterProcessor;
import org.apache.avro.Schema;

public final class MapWriter
    extends AvroWriter
{
    private static final JsonPointer POINTER
        = JsonPointer.of("additionalProperties");

    private static final AvroWriter INSTANCE = new MapWriter();

    private MapWriter()
    {
    }

    public static AvroWriter getInstance()
    {
        return INSTANCE;
    }

    @Override
    protected Schema generate(final AvroWriterProcessor writer,
        final ProcessingReport report, final SchemaTree tree)
        throws ProcessingException
    {
        final SchemaTree schemaTree = tree.append(POINTER);
        final ValueHolder<SchemaTree> input
            = ValueHolder.hold("schema", schemaTree);
        final Schema valueSchema = writer.process(report, input).getValue();
        return Schema.createMap(valueSchema);
    }
}
