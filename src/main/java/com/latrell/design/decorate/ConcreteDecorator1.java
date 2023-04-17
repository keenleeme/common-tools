package com.latrell.design.decorate;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:44
 */
public class ConcreteDecorator1 extends AbstractDecorate {

    public ConcreteDecorator1(Component delegate) {
        super(delegate);
    }

    @Override
    public void operation() {
        System.out.println("加美颜。");
        super.delegate.operation();
    }
}
