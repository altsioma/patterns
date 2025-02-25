package org.lessons.utils;

import org.lessons.commands.ICommand;

public interface IExceptionHandler {
    ICommand handle(ICommand c, Exception e);
}
