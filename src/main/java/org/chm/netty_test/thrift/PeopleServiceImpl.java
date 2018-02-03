package org.chm.netty_test.thrift;

import com.chm.thrift.DataException;
import com.chm.thrift.People;
import com.chm.thrift.PeopleService;
import org.apache.thrift.TException;

/**
 * Created by charming on 2017/6/17.
 */
public class PeopleServiceImpl implements PeopleService.Iface {
    @Override
    public People getPeopleByName(String name) throws DataException, TException {
        System.out.println("name;"+name);
        People people = new People();
        people.setName("zhangsan");
        people.setAge(12);
        people.setMarried(false);
        return people;
    }

    @Override
    public void savePeople(People people) throws DataException, TException {
        System.out.println("-----savaPeople----");
        System.out.println(people.getName());
        System.out.println(people.getAge());
        System.out.println(people.isMarried());
    }
}
