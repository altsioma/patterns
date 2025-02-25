package org.lessons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lessons.commands.*;
import org.lessons.utils.IExceptionHandler;
import org.lessons.utils.ILogger;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Предположим, что все команды находятся в некоторой очереди.
 * Обработка очереди заключается в чтении очередной команды и головы очереди и вызова метода Execute извлеченной команды.
 * Метод Execute() может выбросить любое произвольное исключение.
 * <p>
 * 1. Обернуть вызов Команды в блок try-catch.
 * 2. Обработчик catch должен перехватывать только самое базовое исключение.
 * 3. Есть множество различных обработчиков исключений. Выбор подходящего обработчика исключения делается на основе
 * экземпляра перехваченного исключения и команды, которая выбросила исключение.
 * 4. Реализовать Команду, которая записывает информацию о выброшенном исключении в лог.
 * 5. Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.
 * 6. Реализовать Команду, которая повторяет Команду, выбросившую исключение.
 * 7. Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.
 * 8. С помощью Команд из пункта 4 и пункта 6 реализовать следующую обработку исключений:
 * при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.
 * 9. Реализовать стратегию обработки исключения - повторить два раза, потом записать в лог.
 * Указание: создать новую команду, точно такую же как в пункте 6. Тип этой команды будет показывать, что Команду не удалось выполнить два раза.
 */
class MainTest {
    @Mock
    private IExceptionHandler exceptionHandler;

    @Mock
    private ILogger logger;

    @Mock
    // Команда, которая выбрасывает исключение
    private ICommand failedCmd;

    // Очередь команд
    private final LinkedList<ICommand> queueCommand = new LinkedList<>();

    @BeforeEach
    void initExceptionHandler() {
        MockitoAnnotations.openMocks(this);
        // Обработчик базовых исключений
        exceptionHandler = mock(IExceptionHandler.class);
        // Мок логгера
        logger = mock(ILogger.class);
        // Мок команды, которая выбрасывает исключение
        failedCmd = mock(ICommand.class);

        when(exceptionHandler.handle(any(), any())).thenAnswer((Answer<ICommand>) invocation -> {
            ICommand c = invocation.getArgument(0);
            Exception e = invocation.getArgument(1);

            /*
            3. Есть множество различных обработчиков исключений.
            Выбор подходящего обработчика исключения делается на основе экземпляра перехваченного исключения и
            команды, которая выбросила исключение.

            Далее будут реализованы различные обработчики в зависимости от условий
            (В реальном проекте это был бы store с обработчиками по ключам: _store[сt][et])
             */
            Class<?> ct = c.getClass();
            Class<?> et = e.getClass();


            /*
            5. Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.
            Условием создания обработчика будет тип ошибки IllegalArgumentException (et) и тип команды (ct),
            которая выбросила исключение.
             */
            if (et == IllegalArgumentException.class && ct == failedCmd.getClass()) {
                return new AddToQueueCommand(queueCommand, new WriteToLogCommand(e, logger));
            }

            /*
            7. Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.
            Условием создания обработчика будет тип ошибки RuntimeException (другой ключ et) и тип команды (сt),
            которая выбросила исключение.
             */
            if (et == RuntimeException.class && ct == failedCmd.getClass()) {
                return new AddToQueueCommand(queueCommand, new RepeatCommand(c));
            }

            /*
            8. С помощью Команд из пункта 4 и пункта 6 реализовать следующую обработку исключений:
            при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.

            ===
            Решение:
            В данном случае вначале отработает обработчик из пункта 7, он создаст команду, которая будет выполнена повторно
            Когда команда будет выполнена второй раз, ее тип будет не failCommand а RepeatCommand и на этот тип ошибки
            реализуем следующий обработчик:
             */
            if (et == RuntimeException.class && ct == RepeatCommand.class) {
                return new AddToQueueCommand(queueCommand, new WriteToLogCommand(e, logger));
            }

            /*
            9. Реализовать стратегию обработки исключения - повторить два раза, потом записать в лог.
            Указание: создать новую команду, точно такую же как в пункте 6.
            Тип этой команды будет показывать, что Команду не удалось выполнить два раза.

            ===
            Решение:
            При первом перехваченном исключении NullPointerException для команды failedCmd,
            в очередь будет добавлена команда RepeatTwice, которая повторит выполнении команды
             */
            if (et == NullPointerException.class && ct == failedCmd.getClass()) {
                return new AddToQueueCommand(queueCommand, new RepeatTwice(c));
            }

            /*
            При повторном перехваченном исключении NullPointerException,
            команда будет выполнена не failedCmd, а RepeatTwice и на такую комбинацию
            реализуем следующий обработчик, в результате чего, в очередь будет добавлена команда RepeatCommand,
            которая повторит выполнение команды
             */
            if (et == NullPointerException.class && ct == RepeatTwice.class) {
                return new AddToQueueCommand(queueCommand, new RepeatCommand(c));
            }

            /*
            При перехваченном исключении NullPointerException, для команды RepeatCommand (установлена на прошлом шаге)
            в очередь будет добавлена команда WriteToLogCommand
             */
            if (et == NullPointerException.class && ct == RepeatCommand.class) {
                return new AddToQueueCommand(queueCommand, new WriteToLogCommand(e, logger));
            }

            return new DefaultHandlerCommand(e);
        });
    }

    @Test()
    @DisplayName("Должен быть установлен обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.")
    void testLogHandler() {
        doThrow(new IllegalArgumentException("Illegal argument")).when(failedCmd).execute();

        // 1. Обернуть вызов Команды в блок try-catch.
        // 2. Обработчик catch должен перехватывать только самое базовое исключение.
        try {
            failedCmd.execute();
        } catch (Exception e) {
            exceptionHandler.handle(failedCmd, e).execute();
        }

        // В очереди должна быть установлена команда WriteToLogCommand
        assertEquals(WriteToLogCommand.class, Objects.requireNonNull(queueCommand.poll()).getClass());
    }

    @Test()
    @DisplayName("Должен быть установлен обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.")
    void testRepeatHandler() {
        doThrow(new RuntimeException("Command execution failed")).when(failedCmd).execute();

        // 1. Обернуть вызов Команды в блок try-catch.
        // 2. Обработчик catch должен перехватывать только самое базовое исключение.
        try {
            failedCmd.execute();
        } catch (Exception e) {
            exceptionHandler.handle(failedCmd, e).execute();
        }

        // В очереди должна быть установлена команда RepeatCommand
        assertEquals(RepeatCommand.class, Objects.requireNonNull(queueCommand.poll()).getClass());
    }

    @Test()
    @DisplayName("При первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.")
    void testRepeatAndLogHandler() {
        // Вызов метода execute команды будет выбрасывать исключение RuntimeException
        doThrow(new RuntimeException("Command execution failed")).when(failedCmd).execute();
        // Добавляем Команду в очередь команд
        queueCommand.add(failedCmd);

        while (!queueCommand.isEmpty()) {
            // Получаем текущую Команду из очереди команд
            ICommand cmd = queueCommand.poll();

            // 1. Обернуть вызов Команды в блок try-catch.
            // 2. Обработчик catch должен перехватывать только самое базовое исключение.
            try {
                cmd.execute();
            } catch (Exception e) {
                exceptionHandler.handle(cmd, e).execute();
            }
        }
        // Команда failedCmd будет выполнена два раз: один раз в результате выполнения основного потока команд, второй раз в RepeatCommand
        verify(failedCmd, times(2)).execute();
        // Вызов метода info логгера будет выполнен один раз, по стратегии: выполняем команду повторно, затем пишем лог
        verify(logger, times(1)).info("Записано в лог: Command execution failed");
    }

    @Test()
    @DisplayName("Реализована стратегия обработки исключения - повторить два раза, потом записать в лог.")
    void testRepeatTwiceAndLogHandler() {
        // Вызов метода execute команды будет выбрасывать исключение NullPointerException
        doThrow(new NullPointerException("Command execution failed")).when(failedCmd).execute();
        // Добавляем Команду в очередь команд
        queueCommand.add(failedCmd);

        while (!queueCommand.isEmpty()) {
            // Получаем текущую Команду из очереди команд
            ICommand cmd = queueCommand.poll();

            // 1. Обернуть вызов Команды в блок try-catch.
            // 2. Обработчик catch должен перехватывать только самое базовое исключение.
            try {
                cmd.execute();
            } catch (Exception e) {
                exceptionHandler.handle(cmd, e).execute();
            }
        }

        /*
        Команда failedCmd будет выполнена три раза: один раз в результате выполнения основного потока команд, второй раз в RepeatTwice
        третий раз RepeatCommand
         */
        verify(failedCmd, times(3)).execute();
        // Вызов метода info логгера будет выполнен один раз, по стратегии: выполняем команду повторно два раза, затем пишем лог
        verify(logger, times(1)).info("Записано в лог: Command execution failed");
    }
}
