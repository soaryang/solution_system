package cn.yangtengfei.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Test {
    public static void main(String[] args) {
        // client startup
        Client client = null;
        try {
            //Node node = NodeBuilder.nodeBuilder().node();
            //node.start();
            Settings settings = Settings.settingsBuilder().put("cluster.name", "my-application").build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.130"), 9300));

            //createByJSon(client);
            //findData(client);
            delete(client);
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(Client client){
        DeleteResponse deleteResponse = client.prepareDelete("twitter", "tweet","1").get();
        boolean isd = deleteResponse.isFound();
        System.out.println("isd:"+isd);
    }
    public static void findData(Client client){
        GetResponse response =  client.prepareGet("twitter", "tweet","1").get();
        System.out.println(response.getSource().toString());
    }
    public static void createByJSon(Client client){
        Blog blog =  new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别.22222222222..");
        IndexResponse response = client.prepareIndex("twitter", "tweet",blog.getId()+"")
                .setSource(JSON.toJSONString(blog))
                .get();
    }
    public static void create(Client client) throws IOException {
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        //.field("postDate", new Date())
                        .field("message", "trying out Elasticsearch1")
                        .endObject()
                )
                .get();
    }

    public static void update(Client client) throws IOException, ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest("index", "type", "2")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject());
        client.update(updateRequest).get();
    }
    public static void  createOrUpdate(Client client) throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest("index", "type", "2")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest("index", "type", "2")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("gender", "women")
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();

    }

    public static String  createData(){
        Blog blog =  new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别...");
        return JSON.toJSONString(blog);
    }

}
