package com.latrell.design.decorate;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:41
 */
public class ConcreteComponent implements Component {

    @Override
    public void operation() {
        System.out.println("拍照。");
    }
}
