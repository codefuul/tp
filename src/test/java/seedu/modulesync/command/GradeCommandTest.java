package seedu.modulesync.command;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.modulesync.exception.ModuleSyncException;
import seedu.modulesync.module.ModuleBook;
import seedu.modulesync.storage.Storage;
import seedu.modulesync.ui.Ui;

class GradeCommandTest {

    static class TestStorage extends Storage {
        boolean saved;

        TestStorage(Path path) {
            super(path);
        }

        @Override
        public void save(ModuleBook moduleBook) {
            saved = true;
        }
    }

    static class TestUi extends Ui {
        String message;

        TestUi() {
            super(new java.util.Scanner(new ByteArrayInputStream(new byte[0])));
        }

        @Override
        public void showMessage(String message) {
            this.message = message;
        }
    }

    @Test
    void execute_recordsGrade(@TempDir Path tempDir) throws ModuleSyncException {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.getOrCreate("CS2113").addTodo("Test task");

        TestStorage storage = new TestStorage(tempDir.resolve("modules.txt"));
        TestUi ui = new TestUi();

        GradeCommand command = new GradeCommand("CS2113", "A+");
        command.execute(moduleBook, storage, ui);

        assertEquals("A+", moduleBook.getModule("CS2113").getGrade());
        assertTrue(storage.saved);
        assertTrue(ui.message.contains("A+"));
    }

    @Test
    void execute_recordsGradeInUppercase(@TempDir Path tempDir) throws ModuleSyncException {
        ModuleBook moduleBook = new ModuleBook();
        moduleBook.getOrCreate("CS2113").addTodo("Test task");

        TestStorage storage = new TestStorage(tempDir.resolve("modules.txt"));
        TestUi ui = new TestUi();

        GradeCommand command = new GradeCommand("CS2113", "a+");
        command.execute(moduleBook, storage, ui);

        assertEquals("A+", moduleBook.getModule("CS2113").getGrade());
        assertTrue(storage.saved);
    }

    @Test
    void execute_recordsGradeForNonexistentModule_throws(@TempDir Path tempDir) {
        ModuleBook moduleBook = new ModuleBook();
        TestStorage storage = new TestStorage(tempDir.resolve("modules.txt"));
        TestUi ui = new TestUi();

        GradeCommand command = new GradeCommand("NONEXISTENT", "A+");
        assertThrows(ModuleSyncException.class, () -> command.execute(moduleBook, storage, ui));
    }

    @Test
    void execute_overwritesExistingGrade(@TempDir Path tempDir) throws ModuleSyncException {
        ModuleBook moduleBook = new ModuleBook();
        var module = moduleBook.getOrCreate("CS2113");
        module.addTodo("Test task");
        module.setGrade("B");

        TestStorage storage = new TestStorage(tempDir.resolve("modules.txt"));
        TestUi ui = new TestUi();

        GradeCommand command = new GradeCommand("CS2113", "A");
        command.execute(moduleBook, storage, ui);

        assertEquals("A", moduleBook.getModule("CS2113").getGrade());
        assertTrue(storage.saved);
    }
}
