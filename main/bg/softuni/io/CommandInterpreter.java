package main.bg.softuni.io;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Executable;
import main.bg.softuni.judge.Tester;
import main.bg.softuni.network.DownloadManager;
import main.bg.softuni.repository.StudentsRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandInterpreter {
    private static final String COMMANDS_LOCATION = "src/main/bg/softuni/io/commands/";

    private Tester tester;
    private StudentsRepository repository;
    private DownloadManager downloadManager;
    private IOManager ioManager;

    public CommandInterpreter(Tester tester,
                              StudentsRepository repository,
                              DownloadManager downloadManager,
                              IOManager ioManager) {
        this.tester = tester;
        this.repository = repository;
        this.downloadManager = downloadManager;
        this.ioManager = ioManager;
    }

    public void interpretCommand(String input) throws IOException {
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try {
            Executable command = parseCommand(input, data, commandName);
            command.execute();
        } catch (Exception ex) {
            OutputWriter.displayException(ex.getMessage());
        }
    }

    private Executable parseCommand(String input, String[] data, String command) {
        File commandsFolder = new File(COMMANDS_LOCATION);
        Executable executable = null;
        File[] files = commandsFolder.listFiles();
        for (File file : files) {
            if (!file.isFile() || !file.getName().endsWith(".java")){
                continue;
            }

            try {
                String className = file.getName()
                                .substring(0,file.getName().lastIndexOf("."));
                Class<Executable> exeClass =
                        (Class<Executable>) Class.forName("bg.softuni.io.commands." + className);

                if (!exeClass.isAnnotationPresent(Alias.class)){
                    continue;
                }

                Alias alias = exeClass.getAnnotation(Alias.class);
                String aliasValue = alias.value();
                if (!aliasValue.equalsIgnoreCase(command)){
                    continue;
                }

                Constructor exeCtor = exeClass.getConstructor(String.class, String[].class);
                executable = (Executable) exeCtor.newInstance(input, data);
                this.injectDependancies(executable,exeClass);
            } catch (ReflectiveOperationException rfe){
                rfe.printStackTrace();
            }
        }
        return executable;
    }

    private void injectDependancies(Executable executable, Class<Executable> exeClass) throws IllegalAccessException {
        Field[] exeFields = exeClass.getDeclaredFields();
        for (Field exeField : exeFields) {
            if (!exeField.isAnnotationPresent(Inject.class)){
                continue;
            }

            exeField.setAccessible(true);
            Field[] comInterpFields = CommandInterpreter.class.getDeclaredFields();
            for (Field comInterpField : comInterpFields) {
                if (!comInterpField.getType().equals(exeField.getType())){
                    continue;
                }

                comInterpField.setAccessible(true);
                exeField.set(executable,comInterpField.get(this));
            }
        }

    }
}
