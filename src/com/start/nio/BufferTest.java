package com.start.nio;

import java.nio.IntBuffer;

/**
 * NIO Buffer
 */
public class BufferTest {

    public static void main(String[] args) {
        /*//1.基本操作
        //创建指定长度的缓冲区 ,总共十个区域，对于没有赋值的空区域，值为0
        IntBuffer intBuffer = IntBuffer.allocate(10);
        //放入一个值，还有put的一些重载方法，可以在指定位置插入，或者插入数组,或者在指定位置插入数组
        intBuffer.put(13);//position位置 0 --> 1  position相当于一个游标
        intBuffer.put(21);//position位置 1 --> 2
        intBuffer.put(35);//position位置 2 --> 3
        //把position位置复位到0，且重置limit大小为position游标当前位置，假设游标在x，limit就为x，默认为创建时的容量
        intBuffer.flip();
        System.out.println("使用flip复位："  + intBuffer);// pos:position游标、lim:limit可用元素、cap:capacity容量
        System.out.println("容量为：" + intBuffer.capacity());//容量一旦初始化后无法改变（warp方法包裹数组除外）
        System.out.println("限制为：" + intBuffer.limit());//由于只有三个元素，所以可读取或操作的元素为3，所以limit为3.

        System.out.println("获取下标为1的元素：" + intBuffer.get(1));
        System.out.println("使用get(x)方法，position位置不会改变" + intBuffer);
        intBuffer.put(1,4);//在索引1插入值4,有值则替换
        System.out.println("使用put(index,change)方法，position位置不变" + intBuffer);


        //遍历，使用limit作为长度
        //如果上面不使用flip()方法，此时的limit为10，也就是等于容量大小
        for (int i = 0; i < intBuffer.limit(); i++) {
            //调用get()无参方法，会使position位置往后加一位
            //get()方法是从position游标位置开始获取数据
            System.out.println(intBuffer.get());
        }
        System.out.println("遍历完成后：" + intBuffer);*/


       /* //2. warp()方法的使用
        //warp()方法会包裹一个数组：一般这种用法不会初始化数组对象的长度，因为没有意义，最后还会被warp所包裹的数组覆盖掉。
        // 并且warp()方法修改Buffer对象的时候，数组本身也会跟着发生变化。
        int[] arr = new int[]{1,3,5};
        IntBuffer intBuffer1 = IntBuffer.wrap(arr);
        System.out.println(intBuffer1);

        //这样使用表示容量为arr的长度，但是可操作元素只有实际进入Buffer的元素长度(也就是2)，
        // 其中偏移量(也就是0)表示position游标的初始位置（不能越界，大于等于limit）
        IntBuffer intBuffer2 = IntBuffer.wrap(arr,0,2);
        System.out.println(intBuffer2);*/

        //3.其他方法
        IntBuffer intBuffer3 = IntBuffer.allocate(10);
        int[] arr2 = new int[]{1,3,5};
        //插入数组,游标会后移数组长度值
        intBuffer3.put(arr2);
        System.out.println(intBuffer3);
        //一种复制方法
        IntBuffer intBuffer4 = intBuffer3.duplicate();
        System.out.println(intBuffer4);

        //设置intBuffer3的position游标位置
        intBuffer3.position(1);
        System.out.println(intBuffer3);

        //余下的数据(可读数据)，也就是从position到limit
        System.out.println("可读数据为:" + intBuffer3.remaining());

        //创建一个和余下数据大小相同容量的数组
        int[] arr3 = new int[intBuffer3.remaining()];
        //将Buffer的数据放到arr3数组中去
        intBuffer3.get(arr3);
        for (int i : arr3){
            System.out.println(i + ",");
        }

    }
}
