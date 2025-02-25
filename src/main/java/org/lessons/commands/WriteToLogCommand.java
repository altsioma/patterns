package org.lessons.commands;

import org.lessons.utils.ILogger;

/**
 * 4. Реализовать Команду, которая записывает информацию о выброшенном исключении в лог.
 * Реализует Команду, которая записывает информацию о выброшенном исключении в лог.
 */
public class WriteToLogCommand implements ICommand {
    private final Exception exception;
    private final ILogger logger;

    public WriteToLogCommand(Exception e, ILogger l) {
        exception = e;
        logger = l;
    }

    @Override
    public void execute() {
        logger.info("Записано в лог: " + exception.getMessage());
    }
}
