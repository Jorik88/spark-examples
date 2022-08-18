package org.example.jdbc;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class ReadFromJDBCExample {

    public static void main(String[] args) {
        ReadFromJDBCExample job = new ReadFromJDBCExample();
        job.start();
    }

    private void start() {
        SparkSession spark = SparkSession.builder()
                .appName("streaming example")
                .master("local[3]")
                .getOrCreate();

        Dataset<Row> load = spark.read()
                .format("jdbc")
                .option("driver", "com.mysql.cj.jdbc.Driver")
                .option("url", "jdbc:mysql://localhost:3306/exampleDb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                .option("dbtable", "person")
                .option("user", "example")
                .option("password", "example")
                .load();

        Dataset<Row> rowDataset = load.orderBy("first_name");
        rowDataset.show();
    }
}
