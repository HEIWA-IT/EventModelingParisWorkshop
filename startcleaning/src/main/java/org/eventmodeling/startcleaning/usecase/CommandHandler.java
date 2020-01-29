package org.eventmodeling.startcleaning.usecase;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
