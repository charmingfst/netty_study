package org.chm.netty_test.thrift;

import com.chm.thrift.PeopleService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by charming on 2017/6/17.
 */
public class ThriftServer {
    public static void main(String[] args) {
        try {

            TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);

            THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
            PeopleService.Processor<PeopleServiceImpl> processor = new PeopleService.Processor<>(new PeopleServiceImpl());

            arg.protocolFactory(new TCompactProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));

            TServer server = new THsHaServer(arg);
            System.out.println("thrift server started");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
