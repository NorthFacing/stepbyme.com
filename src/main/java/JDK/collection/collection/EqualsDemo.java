package JDK.collection.collection;

import java.util.ArrayList;
import org.junit.Assert;

/**
 * Created by Bob on 2016/8/5.
 */
public class EqualsDemo {
  public static void main(String[] args) {
    // 基本数据类型
    ArrayList<String> temp1 = new ArrayList<>();
    temp1.add("A");
    ArrayList<String> temp2 = new ArrayList<>();
    temp2.add("A");
    temp2.add("B");
    boolean equals = temp1.equals(temp2);
    Assert.assertFalse(equals);
    temp1.add("B");
    equals = temp1.equals(temp2);
    Assert.assertTrue(equals);

    // 引用对象类型
    // —— 没有覆写元素equals方法的情形
    Teacher teacher1 = new Teacher(110);
    Teacher teacher2 = new Teacher(110);

    ArrayList<Teacher> teachers11 = new ArrayList<>();
    ArrayList<Teacher> teachers12 = new ArrayList<>();
    ArrayList<Teacher> teachers2 = new ArrayList<>();
    teachers11.add(teacher1);
    teachers12.add(teacher1);

    equals = teachers11.equals(teachers12);     // true：同一个对象放入两个不同的集合
    Assert.assertTrue(equals);
    equals = teachers11.equals(teacher2);       // false：不同对象放入不同的集合，虽然cardNum相同，但是仍然不相等
    Assert.assertFalse(equals);

    // —— 覆写元素equals方法的情形
    ArrayList<Student> students11 = new ArrayList<>();
    ArrayList<Student> students12 = new ArrayList<>();
    ArrayList<Student> students2 = new ArrayList<>();
    Student student1 = new Student(1);
    Student student2 = new Student(1);

    students11.add(student1);
    students12.add(student1);
    students2.add(student2);

    equals = students11.equals(students12);     // true
    Assert.assertTrue(equals);

    equals = students11.equals(students2);         // true
    Assert.assertTrue(equals);

  }
}

/**
 * 不覆写equals方法
 */
class Teacher {
  private Integer cardNum;

  Teacher(Integer cardNum) {
    this.cardNum = cardNum;
  }
}

/**
 * 覆写equals方法
 */
class Student {
  private Integer cardNum;

  Student(Integer cardNum) {
    this.cardNum = cardNum;
  }

  @Override
  public boolean equals(Object object) { // 学号相同就认为是同一个学生
    Student student = (Student) object;
    if (this.cardNum == student.cardNum)
      return true;
    else
      return false;
  }
}