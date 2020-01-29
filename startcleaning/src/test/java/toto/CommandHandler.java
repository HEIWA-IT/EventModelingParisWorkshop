package toto;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
