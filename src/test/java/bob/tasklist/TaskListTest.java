package bob.tasklist;

import bob.exception.BobException;
import bob.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskList Tests")
class TaskListTest {
    
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    @DisplayName("TaskList: should be empty initially")
    void testTaskListEmpty() {
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: should add task correctly")
    void testAddTask() {
        Todo todo = new Todo("buy groceries");
        taskList.addTask(todo);
        
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: should add multiple tasks")
    void testAddMultipleTasks() {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Todo("task2"));
        taskList.addTask(new Todo("task3"));
        
        assertEquals(3, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: should get all tasks")
    void testGetAllTasks() {
        Todo todo1 = new Todo("task1");
        Todo todo2 = new Todo("task2");
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        
        List<Task> tasks = taskList.getAllTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(todo1));
        assertTrue(tasks.contains(todo2));
    }

    @Test
    @DisplayName("TaskList: should delete task by index")
    void testDeleteTask() throws BobException {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Todo("task2"));
        taskList.addTask(new Todo("task3"));
        
        Task deleted = taskList.deleteTask(1);
        assertEquals(2, taskList.getSize());
        assertTrue(deleted.toString().contains("task2"));
    }

    @Test
    @DisplayName("TaskList: should throw when deleting invalid index")
    void testDeleteTaskInvalidIndex() {
        taskList.addTask(new Todo("task1"));
        
        assertThrows(BobException.class, () -> {
            taskList.deleteTask(5);
        });
    }

    @Test
    @DisplayName("TaskList: should throw when deleting from negative index")
    void testDeleteTaskNegativeIndex() {
        taskList.addTask(new Todo("task1"));
        
        assertThrows(BobException.class, () -> {
            taskList.deleteTask(-1);
        });
    }

    @Test
    @DisplayName("TaskList: should get task by index")
    void testGetTask() throws BobException {
        Todo todo1 = new Todo("task1");
        Todo todo2 = new Todo("task2");
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        
        Task retrieved = taskList.getTask(0);
        assertTrue(retrieved.toString().contains("task1"));
    }

    @Test
    @DisplayName("TaskList: should throw when getting invalid index")
    void testGetTaskInvalidIndex() {
        taskList.addTask(new Todo("task1"));
        
        assertThrows(BobException.class, () -> {
            taskList.getTask(5);
        });
    }

    @Test
    @DisplayName("TaskList: should mark task as done")
    void testMarkTask() throws BobException {
        Todo todo = new Todo("task1");
        taskList.addTask(todo);
        
        taskList.markTask(0);
        assertTrue(taskList.getTask(0).toString().contains("[X]"));
    }

    @Test
    @DisplayName("TaskList: should throw when marking invalid index")
    void testMarkTaskInvalidIndex() {
        taskList.addTask(new Todo("task1"));
        
        assertThrows(BobException.class, () -> {
            taskList.markTask(5);
        });
    }

    @Test
    @DisplayName("TaskList: should unmark task as done")
    void testUnmarkTask() throws BobException {
        Todo todo = new Todo("task1");
        taskList.addTask(todo);
        taskList.markTask(0);
        
        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).toString().contains("[X]"));
    }

    @Test
    @DisplayName("TaskList: should throw when unmarking invalid index")
    void testUnmarkTaskInvalidIndex() {
        taskList.addTask(new Todo("task1"));
        
        assertThrows(BobException.class, () -> {
            taskList.unmarkTask(5);
        });
    }

    @Test
    @DisplayName("TaskList: should find tasks by deadline date")
    void testFindTasksByDateDeadline() {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Deadline("submit", "2024-03-15"));
        taskList.addTask(new Deadline("another", "2024-03-20"));
        taskList.addTask(new Todo("task2"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 15));
        assertEquals(1, found.size());
        assertTrue(found.get(0).toString().contains("submit"));
    }

    @Test
    @DisplayName("TaskList: should find tasks by event date range")
    void testFindTasksByDateEvent() {
        taskList.addTask(new Event("meeting", "2024-03-10", "2024-03-15"));
        taskList.addTask(new Todo("task"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 12));
        assertEquals(1, found.size());
        assertTrue(found.get(0).toString().contains("meeting"));
    }

    @Test
    @DisplayName("TaskList: should not find tasks on different dates")
    void testFindTasksByDateNoMatch() {
        taskList.addTask(new Deadline("submit", "2024-03-15"));
        taskList.addTask(new Todo("task"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 20));
        assertEquals(0, found.size());
    }

    @Test
    @DisplayName("TaskList: should find multiple tasks on same date")
    void testFindTasksByDateMultiple() {
        taskList.addTask(new Deadline("submit1", "2024-03-15"));
        taskList.addTask(new Deadline("submit2", "2024-03-15"));
        taskList.addTask(new Event("event", "2024-03-15", "2024-03-16"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 15));
        assertEquals(3, found.size());
    }

    @Test
    @DisplayName("TaskList: should not find todos by date search")
    void testFindTasksByDateTodoNotFound() {
        taskList.addTask(new Todo("task"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 15));
        assertEquals(0, found.size());
    }

    @Test
    @DisplayName("TaskList: constructor should initialize with tasks")
    void testTaskListConstructorWithTasks() {
        TaskList list1 = new TaskList();
        list1.addTask(new Todo("task1"));
        list1.addTask(new Todo("task2"));
        
        TaskList list2 = new TaskList(list1.getAllTasks());
        assertEquals(2, list2.getSize());
    }

    @Test
    @DisplayName("TaskList: getAllTasks should return copy not reference")
    void testGetAllTasksReturnsCopy() {
        taskList.addTask(new Todo("task1"));
        List<Task> tasks1 = taskList.getAllTasks();
        List<Task> tasks2 = taskList.getAllTasks();
        
        assertNotSame(tasks1, tasks2);
        assertEquals(tasks1.size(), tasks2.size());
    }

    @Test
    @DisplayName("TaskList: should handle edge case of empty find")
    void testFindTasksEmptyList() {
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 15));
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("TaskList: should find event at boundary dates")
    void testFindTasksByDateEventBoundary() {
        taskList.addTask(new Event("meeting", "2024-03-10", "2024-03-15"));
        
        List<Task> foundStart = taskList.findTasksByDate(LocalDate.of(2024, 3, 10));
        assertEquals(1, foundStart.size());
        
        List<Task> foundEnd = taskList.findTasksByDate(LocalDate.of(2024, 3, 15));
        assertEquals(1, foundEnd.size());
    }

    // Additional tests for A-MoreTesting increment

    @Test
    @DisplayName("TaskList: should find tasks by keyword case-insensitive")
    void testFindTasksByKeywordCaseInsensitive() {
        taskList.addTask(new Todo("Buy Milk"));
        taskList.addTask(new Todo("buy bread"));
        taskList.addTask(new Deadline("submit assignment", "2024-03-15"));
        
        List<Task> found = taskList.findTasksByKeyword("BUY");
        assertEquals(2, found.size());
    }

    @Test
    @DisplayName("TaskList: should find tasks by partial keyword")
    void testFindTasksByPartialKeyword() {
        taskList.addTask(new Todo("reading homework"));
        taskList.addTask(new Todo("book report"));
        taskList.addTask(new Deadline("read chapter 5", "2024-03-15"));
        
        List<Task> found = taskList.findTasksByKeyword("read");
        assertEquals(2, found.size());
    }

    @Test
    @DisplayName("TaskList: should return empty list when no keywords match")
    void testFindTasksByKeywordNoMatch() {
        taskList.addTask(new Todo("buy milk"));
        taskList.addTask(new Todo("wash car"));
        
        List<Task> found = taskList.findTasksByKeyword("homework");
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("TaskList: should find tasks in all task types")
    void testFindTasksByKeywordAllTypes() {
        taskList.addTask(new Todo("project work"));
        taskList.addTask(new Deadline("project submission", "2024-03-15"));
        taskList.addTask(new Event("project meeting", "2024-03-10", "2024-03-12"));
        
        List<Task> found = taskList.findTasksByKeyword("project");
        assertEquals(3, found.size());
    }

    @Test
    @DisplayName("TaskList: should sort tasks alphabetically")
    void testSortTasks() {
        taskList.addTask(new Todo("zebra"));
        taskList.addTask(new Todo("apple"));
        taskList.addTask(new Todo("monkey"));
        
        taskList.sortTasks();
        
        List<Task> tasks = taskList.getAllTasks();
        assertTrue(tasks.get(0).getDescription().equals("apple"));
        assertTrue(tasks.get(1).getDescription().equals("monkey"));
        assertTrue(tasks.get(2).getDescription().equals("zebra"));
    }

    @Test
    @DisplayName("TaskList: should sort tasks case-insensitively")
    void testSortTasksCaseInsensitive() {
        taskList.addTask(new Todo("Zebra"));
        taskList.addTask(new Todo("apple"));
        taskList.addTask(new Todo("Banana"));
        
        taskList.sortTasks();
        
        List<Task> tasks = taskList.getAllTasks();
        assertEquals("apple", tasks.get(0).getDescription());
        assertEquals("Banana", tasks.get(1).getDescription());
        assertEquals("Zebra", tasks.get(2).getDescription());
    }

    @Test
    @DisplayName("TaskList: should handle sorting empty list")
    void testSortTasksEmptyList() {
        assertDoesNotThrow(() -> taskList.sortTasks());
        assertTrue(taskList.isEmpty());
    }

    @Test
    @DisplayName("TaskList: should handle sorting single task")
    void testSortTasksSingleTask() {
        taskList.addTask(new Todo("only task"));
        taskList.sortTasks();
        assertEquals(1, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: sort should preserve task completion status")
    void testSortPreservesCompletionStatus() throws BobException {
        Todo task1 = new Todo("zebra");
        task1.markAsDone();
        taskList.addTask(task1);
        taskList.addTask(new Todo("apple"));
        
        taskList.sortTasks();
        
        Task firstTask = taskList.getTask(0);
        assertEquals("apple", firstTask.getDescription());
        assertFalse(firstTask.isDone());
        
        Task secondTask = taskList.getTask(1);
        assertEquals("zebra", secondTask.getDescription());
        assertTrue(secondTask.isDone());
    }

    @Test
    @DisplayName("TaskList: should add multiple tasks with varargs")
    void testAddMultipleTasksVarargs() {
        Todo task1 = new Todo("task1");
        Todo task2 = new Todo("task2");
        Todo task3 = new Todo("task3");
        
        taskList.addTask(task1, task2, task3);
        assertEquals(3, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: should find event not before date range")
    void testFindTasksByDateEventNotBeforeRange() {
        taskList.addTask(new Event("meeting", "2024-03-10", "2024-03-15"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 9));
        assertEquals(0, found.size());
    }

    @Test
    @DisplayName("TaskList: should find event not after date range")
    void testFindTasksByDateEventNotAfterRange() {
        taskList.addTask(new Event("meeting", "2024-03-10", "2024-03-15"));
        
        List<Task> found = taskList.findTasksByDate(LocalDate.of(2024, 3, 16));
        assertEquals(0, found.size());
    }

    @Test
    @DisplayName("TaskList: constructor should create independent copy")
    void testTaskListConstructorCreatesIndependentCopy() {
        TaskList list1 = new TaskList();
        list1.addTask(new Todo("task1"));
        
        TaskList list2 = new TaskList(list1.getAllTasks());
        list2.addTask(new Todo("task2"));
        
        assertEquals(1, list1.getSize());
        assertEquals(2, list2.getSize());
    }

    @Test
    @DisplayName("TaskList: delete should update size correctly")
    void testDeleteUpdatesSize() throws BobException {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Todo("task2"));
        taskList.addTask(new Todo("task3"));
        
        assertEquals(3, taskList.getSize());
        taskList.deleteTask(1);
        assertEquals(2, taskList.getSize());
    }

    @Test
    @DisplayName("TaskList: should throw when getting from empty list")
    void testGetTaskFromEmptyList() {
        assertThrows(BobException.class, () -> {
            taskList.getTask(0);
        });
    }

    @Test
    @DisplayName("TaskList: findByKeyword should handle empty keyword")
    void testFindTasksByEmptyKeyword() {
        taskList.addTask(new Todo("task"));
        List<Task> found = taskList.findTasksByKeyword("");
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("TaskList: sort should handle already sorted list")
    void testSortAlreadySortedList() {
        taskList.addTask(new Todo("apple"));
        taskList.addTask(new Todo("banana"));
        taskList.addTask(new Todo("cherry"));
        
        taskList.sortTasks();
        
        List<Task> tasks = taskList.getAllTasks();
        assertEquals("apple", tasks.get(0).getDescription());
        assertEquals("banana", tasks.get(1).getDescription());
        assertEquals("cherry", tasks.get(2).getDescription());
    }
}
