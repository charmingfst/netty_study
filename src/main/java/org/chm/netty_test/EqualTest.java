package org.chm.netty_test;

import java.util.ArrayList;

/**
 * Created by charming on 2017/6/29.
 */

class People{
    private String name = "张三";
    private int age = 23;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  People)
        {
            People people = (People) obj;
            if (people.getName().equals(this.name) && people.getAge() == this.age)
                return true;
            else
                return false;
        }else {
            return false;
        }

    }
}

public class EqualTest {


    public static void main(String[] args) {
        ArrayList<People> list = new ArrayList<>();
        People people = new People();
        people.setName("lisi");
        people.setAge(20);

        list.add(people);

        System.out.println(people == list.get(0));
        System.out.println(people.equals(list.get(0)));

        System.out.println("===========");

        People people1 = list.get(0);
        people1.setAge(33);

        System.out.println(people == people1);
        System.out.println(people.equals(people1));


        People people2 = new People();
        people2.setName("lisi");
        people2.setAge(33);

        System.out.println("===========");


        System.out.println(people == people2);
        System.out.println(people.equals(people2));

        //，复合数据类型（类），当他们用（==）进行比较的时候，比较的是他们在内存中的存放地址，所以，除非是同一个new出来的对象，他们的比较后的结果为true，否则比较后结果为false。
        // Object的equals方法也是用双等号（==）进行比较的,对于复合数据类型之间进行equals比较，在没有覆写equals方法的情况下，他们之间的比较还是基于他们在内存中的存放位置的地址值的。
        //对于对象类型 如果没有覆写equals方法，比较的是他们在内存中的存放地址，如果覆写了equals方法就按equals方法的逻辑进行比较。



    }
}
