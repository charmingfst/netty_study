package org.chm.netty_test.thrift;

import com.chm.thrift.People;
import com.chm.thrift.PeopleService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by charming on 2017/6/17.
 */
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PeopleService.Client client = new PeopleService.Client(protocol);
        try {
            transport.open();

            People people = client.getPeopleByName("zhangsan");
            System.out.println(people.getAge());
            System.out.println(people.isMarried());

            People people1 = new People();
            people1.setName("李四");
            people1.setAge(32);
            people1.setMarried(true);

            client.savePeople(people1);

        }catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            transport.close();
        }
    }
}
