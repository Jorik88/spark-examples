package org.example.file_streaming;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.StringType;

public class StructuredStreamingFromFileExample {
    
    public static void main(String[] args) throws StreamingQueryException {
        StructuredStreamingFromFileExample example = new StructuredStreamingFromFileExample();
        example.start();
    }

    private void start() throws StreamingQueryException {
        SparkSession spark = SparkSession.builder()
                .appName("streaming example")
                .master("local[3]")
                .getOrCreate();

        StructType schema = new StructType()
                .add(DataTypes.createStructField("RecordNumber", IntegerType, true))
                .add(DataTypes.createStructField("Zipcode", StringType, true))
                .add(DataTypes.createStructField("ZipCodeType", StringType, true))
                .add(DataTypes.createStructField("City", StringType, true))
                .add(DataTypes.createStructField("State", StringType, true))
                .add(DataTypes.createStructField("LocationType", StringType, true))
                .add(DataTypes.createStructField("Lat", StringType, true))
                .add(DataTypes.createStructField("Long", StringType, true))
                .add(DataTypes.createStructField("Xaxis", StringType, true))
                .add(DataTypes.createStructField("Yaxis", StringType, true))
                .add(DataTypes.createStructField("Zaxis", StringType, true))
                .add(DataTypes.createStructField("WorldRegion", StringType, true))
                .add(DataTypes.createStructField("Country", StringType, true))
                .add(DataTypes.createStructField("LocationText", StringType, true))
                .add(DataTypes.createStructField("Location", StringType, true))
                .add(DataTypes.createStructField("Decommisioned", StringType, true)
                );


        Dataset<Row> result = spark.readStream()
                .schema(schema)
                .json("src/main/java/org/example/file_streaming/resources");


        Dataset<Row> groupDF = result.select("Zipcode")
                .groupBy("Zipcode").count();
        groupDF.printSchema();

        groupDF.writeStream()
                .format("console")
                .outputMode("complete")
                .start()
                .awaitTermination();
    }
}
