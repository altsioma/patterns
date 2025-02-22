package org.lessons.commands;

/**
 * Команда, которая повторяет выполнение Команды. Необходима для обработки стратегии двойного повторения команды.
 */
public class RepeatTwice implements ICommand {
    private final ICommand cmd;

    public RepeatTwice(ICommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        this.cmd.execute();
    }
}
