import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class UploadImg {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Configuration conf=new Configuration();
        conf.set("dfs.block.size","10485760");
        conf.set("dfs.replication","1");
        URI uri = new URI("hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(uri,conf);
        Path resP = new Path("C://data/c350bip");
        Path destP = new Path("/img");
        if(!fs.exists(destP)){
            fs.mkdirs(destP);
        }
        fs.copyFromLocalFile(resP, destP);
//        fs.delete(new Path("/img/c350bip"),true);
        fs.close();


    }
}
