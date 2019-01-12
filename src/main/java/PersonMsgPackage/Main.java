package PersonMsgPackage;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        PersonMsgClassName.Person.Builder builder = PersonMsgClassName.Person.newBuilder();
        builder.setId(111);
        builder.setName("molice");

/*        PersonMsgClassName.Person.Friend.Builder friendBuilder = PersonMsgClassName.Person.Friend.newBuilder();
        friendBuilder.setAge(11);
        friendBuilder.setName("wqqeq");
        builder.addFriends(friendBuilder);

        System.out.println(builder.isInitialized());*/

        PersonMsgClassName.Person build = builder.build();
        // 将数据写到输出流，如网络输出流，这里就用ByteArrayOutputStream来代替
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        build.writeTo(output);

        // -------------- 分割线：上面是发送方，将数据序列化后发送 ---------------

        byte[] bytes = output.toByteArray();

        Person person1 = new Person();
        person1.setId(111);
        person1.setName("molice");
        String s = JSON.toJSONString(person1);
        System.out.println("json:"+s.length());
        System.out.println("proto:"+bytes.length);
        // -------------- 分割线：下面是接收方，将数据接收后反序列化 ---------------

        // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        PersonMsgClassName.Person person = PersonMsgClassName.Person.parseFrom(input);
        //PersonMsgClassName.Person person = PersonMsgClassName.Person.parseFrom(bytes);
        System.out.println(person.getId());
        System.out.println(person.getName());

        List<PersonMsgClassName.Person.Friend> friendsList = person.getFriendsList();
        for (PersonMsgClassName.Person.Friend friend : friendsList) {
            System.out.println(friend.getAge());
            System.out.println(friend.getName());
        }

        Person person2 = JSON.parseObject(s, Person.class);
        System.out.println(person2.getId());
        System.out.println(person2.getName());
    }
}
