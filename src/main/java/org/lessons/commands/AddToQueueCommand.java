package org.lessons.commands;

import java.util.LinkedList;

/**
 * Реализует Команду, которая добавляет Команду в очередь команд.
 */
public class AddToQueueCommand implements ICommand {
    private final LinkedList<ICommand> q;
    private final ICommand cmd;

    public AddToQueueCommand(LinkedList<ICommand> q, ICommand cmd) {
        this.q = q;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        this.q.add(this.cmd);
    }
}
