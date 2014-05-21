package com.tinkerpop.gremlin.giraph.structure.io.tinkergraph;

import org.apache.giraph.io.VertexInputFormat;
import org.apache.giraph.io.VertexReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class TinkerGraphInputFormat extends VertexInputFormat {

    // public static final String GIRAPH_GREMLIN_INPUT_READER = "giraph.gremlin.inputReader";

    public void checkInputSpecs(Configuration conf) {

    }

    public List<InputSplit> getSplits(final JobContext context, final int minSplitCountHint) throws IOException, InterruptedException {
        return Arrays.<InputSplit>asList(new DBInputFormat.DBInputSplit());
    }

    public VertexReader createVertexReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new TinkerGraphVertexReader();
    }

}
