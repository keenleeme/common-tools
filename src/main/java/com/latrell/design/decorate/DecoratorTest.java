package com.latrell.design.decorate;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:45
 */
public class DecoratorTest {

    public static void main(String[] args) {
        Component delegate = new ConcreteDecorator1(new ConcreteComponent());
        delegate.operation();
    }

}
