package com.latrell.design.decorate;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:45
 */
public class ConcreteDecorator2 extends AbstractDecorate {

    public ConcreteDecorator2(Component delegate) {
        super(delegate);
    }

    @Override
    public void operation() {
        System.out.println("加滤镜。");
        super.delegate.operation();
    }
}
