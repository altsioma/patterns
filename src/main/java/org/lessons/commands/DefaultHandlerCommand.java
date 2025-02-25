package org.lessons.commands;

/**
 * Реализует команду, которая будет использована в качестве обработчика ошибок по-умолчанию.
 */
public class DefaultHandlerCommand implements ICommand {
    private final Exception exception;

    public DefaultHandlerCommand(Exception e) {
        exception = e;
    }

    @Override
    public void execute() {
        System.out.println("Ошибка: " + exception.getMessage());
    }
}
