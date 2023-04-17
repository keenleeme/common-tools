package com.latrell.design.decorate;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/22-22:43
 */
public abstract class AbstractDecorate implements Component {

    protected Component delegate;

    public AbstractDecorate(Component delegate) {
        this.delegate = delegate;
    }
}
