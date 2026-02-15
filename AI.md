# Use of AI Tools in Bob Project

This document records the use of AI tools in the development of the Bob chatbot project.

## AI Tool Used

**GitHub Copilot** - An AI-powered code completion tool that suggests code snippets and entire functions based on context and comments.

## Features Developed with AI Assistance

### 1. A-Assertions (Level 9)

**Date**: February 2026

**AI Tool**: GitHub Copilot

**How it helped**:
- Suggested appropriate assertion statements for validating assumptions in critical code paths
- Helped identify edge cases that needed validation
- Generated assertion messages that clearly describe the expected conditions

**Files Modified**:
- `src/main/java/bob/parser/Parser.java`: Added assertions for index conversion validation
- `src/main/java/bob/storage/Storage.java`: Added assertions for data integrity during file I/O operations
- `src/main/java/bob/task/Task.java`: Added assertions for state transitions (mark/unmark operations)
- `src/main/java/bob/tasklist/TaskList.java`: Added assertions for validating indices and non-null tasks
- `build.gradle`: Enabled assertions in run configuration

**Example**:
```java
// In TaskList.java - AI suggested this assertion pattern
public void deleteTask(int index) {
    assert index >= 0 && index < tasks.size() : "Index must be within valid range";
    Task removedTask = tasks.remove(index);
    assert removedTask != null : "Removed task should not be null";
}
```

### 2. A-CodeQuality (CheckStyle Compliance)

**Date**: February 2026

**AI Tool**: GitHub Copilot

**How it helped**:
- Identified and fixed 197 CheckStyle violations across 21 files
- Suggested proper import organization and removal of unused imports
- Helped fix indentation issues in switch-case statements
- Generated proper Javadoc comments for classes and methods
- Fixed trailing whitespace and line ending issues

**Key Improvements**:
- Import statement organization
- Switch-case indentation
- Javadoc positioning and formatting
- Static field declaration order
- Method spacing and annotation placement

**Files Modified**: 21 files including all command classes, parser, storage, task classes, and UI components

### 3. A-Streams (Java Streams API)

**Date**: February 2026

**AI Tool**: GitHub Copilot

**How it helped**:
- Converted traditional for-loops to functional-style Stream operations
- Suggested appropriate Stream methods (filter, map, forEach, etc.)
- Helped implement complex operations like date filtering using Streams
- Generated lambda expressions for cleaner code

**Files Modified**:
- `src/main/java/bob/tasklist/TaskList.java`: 
  - Converted `addTask()` to use Stream counting
  - Refactored `findTasksByDate()` with filter and collect
  - Refactored `findTasksByKeyword()` with filter operations
  - Updated `sortTasks()` to use Stream sorting

- `src/main/java/bob/storage/Storage.java`:
  - Converted `save()` method to use Stream pipeline with map and forEach
  
- `src/main/java/bob/ui/Ui.java`:
  - Updated output methods to use Arrays.stream() and IntStream.range()
  - Made message printing more functional

**Example**:
```java
// In TaskList.java - AI suggested converting this loop to Stream
// Before:
for (Task task : tasks) {
    if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
        matchingTasks.add(task);
    }
}

// After (with AI assistance):
return tasks.stream()
    .filter(task -> task.toString().toLowerCase().contains(keyword.toLowerCase()))
    .collect(Collectors.toCollection(ArrayList::new));
```

### 4. C-Sort (Extension Feature)

**Date**: February 2026

**AI Tool**: GitHub Copilot

**How it helped**:
- Suggested the implementation approach using Comparator.comparing()
- Generated the SortCommand class structure following the command pattern
- Helped integrate the sort functionality into the parser and UI
- Suggested case-insensitive alphabetical sorting

**Files Created/Modified**:
- `src/main/java/bob/command/SortCommand.java`: New command class created with AI assistance
- `src/main/java/bob/command/CommandType.java`: Added SORT enum value
- `src/main/java/bob/tasklist/TaskList.java`: Added `sortTasks()` method
- `src/main/java/bob/parser/Parser.java`: Added sort command parsing
- `src/main/java/bob/ui/Ui.java`: Added sort success message

**Example**:
```java
// In TaskList.java - AI suggested this sorting approach
public void sortTasks() {
    tasks.sort(Comparator.comparing(task -> 
        task.toString().toLowerCase()));
}
```

### 5. Error Handling and Robustness

**AI Tool**: GitHub Copilot

**How it helped**:
- Suggested handling corrupted data file entries gracefully
- Generated informative error messages
- Helped implement proper exception handling in file I/O operations
- Suggested validation for date parsing and format checking

**Files Modified**:
- `src/main/java/bob/storage/Storage.java`: Enhanced load() method with comprehensive error handling

### 6. Testing

**AI Tool**: GitHub Copilot

**How it helped**:
- Generated unit test cases for parser, storage, task, and tasklist classes
- Suggested edge cases to test
- Helped create test data and expected outcomes

**Files Modified**:
- `src/test/java/bob/parser/ParserTest.java`
- `src/test/java/bob/storage/StorageTest.java`
- `src/test/java/bob/task/TaskTest.java`
- `src/test/java/bob/tasklist/TaskListTest.java`

## Benefits of Using AI Tools

1. **Faster Development**: AI suggestions significantly reduced the time needed to write boilerplate code and common patterns.

2. **Code Quality**: AI helped maintain consistent code style and suggested best practices.

3. **Learning**: By reviewing AI suggestions, learned better coding patterns and Java features (e.g., Streams API usage).

4. **Bug Prevention**: AI-suggested assertions and error handling helped catch potential bugs early.

5. **Refactoring**: AI made it easier to refactor code to use modern Java features like Streams.

## Verification

All AI-generated code was:
- Carefully reviewed before committing
- Tested to ensure correctness
- Modified when necessary to fit the project requirements
- Documented with appropriate comments

The AI tool was used as an assistant, not a replacement for understanding the code. Each suggestion was evaluated for correctness, efficiency, and maintainability before being accepted.
