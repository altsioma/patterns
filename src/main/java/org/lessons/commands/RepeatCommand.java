package org.lessons.commands;

/**
 * 6. Реализовать Команду, которая повторяет Команду, выбросившую исключение.
 * Реализует Команду, которая повторяет Команду, выбросившую исключение.
 */
public class RepeatCommand implements ICommand {
    private final ICommand cmd;

    public RepeatCommand(ICommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        this.cmd.execute();
    }
}
