package seedu.modulesync.command;

import seedu.modulesync.module.ModuleBook;
import seedu.modulesync.storage.Storage;
import seedu.modulesync.ui.Ui;

public class ListCommand extends Command {
    private final String notDoneModuleCode;

    public ListCommand() {
        this.notDoneModuleCode = null;
    }

    public ListCommand(String notDoneModuleCode) {
        this.notDoneModuleCode = notDoneModuleCode;
    }

    @Override
    public void execute(ModuleBook moduleBook, Storage storage, Ui ui) {
        if (notDoneModuleCode == null) {
            ui.showTaskList(moduleBook);
            return;
        }
        ui.showNotDoneTaskList(moduleBook, notDoneModuleCode);
    }
}
