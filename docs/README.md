# Bob User Guide

![Ui](Ui.png)

## Introduction

**Bob** is a desktop task management chatbot with a graphical user interface (GUI).  
It helps you stay on top of your todos, deadlines, and events using simple text commands.

Bob supports:
- Adding tasks (Todo, Deadline, Event)
- Marking tasks as done or not done
- Searching, sorting, and deleting tasks
- Automatic saving and loading of your task list

---

## Quick Start

1. Ensure you have **Java 17 or above** installed.
2. Download the latest `bob.jar` from the [releases page](https://github.com/p12010304/ip/releases).
3. Open a terminal and run:
   ```
   java -jar bob.jar
   ```
4. Type commands into the input box and press **Enter** or click **Send**.
5. Refer to the Features section below for all available commands.

---

## Features

### Adding a todo task: `todo`

Adds a task without any date.

**Format:**
```
todo DESCRIPTION
```

**Example:**
```
todo Read CS2103 textbook
```

**Expected output:**
```
Got it. I've added this task:
  [T][ ] Read CS2103 textbook
Now you have 1 task(s) in the list.
```

---

### Adding a deadline: `deadline`

Adds a task with a specific due date.

**Format:**
```
deadline DESCRIPTION /by YYYY-MM-DD
```

**Example:**
```
deadline Submit assignment /by 2026-03-01
```

**Expected output:**
```
Got it. I've added this task:
  [D][ ] Submit assignment (by: Mar 1 2026)
Now you have 2 task(s) in the list.
```

---

### Adding an event: `event`

Adds a task with a start date and an end date.

**Format:**
```
event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD
```

**Example:**
```
event Project meeting /from 2026-03-10 /to 2026-03-12
```

**Expected output:**
```
Got it. I've added this task:
  [E][ ] Project meeting (from: Mar 10 2026 to: Mar 12 2026)
Now you have 3 task(s) in the list.
```

---

### Listing all tasks: `list`

Displays all tasks currently in your list.

**Format:**
```
list
```

**Expected output:**
```
Here are the tasks in your list:
1. [T][ ] Read CS2103 textbook
2. [D][ ] Submit assignment (by: Mar 1 2026)
3. [E][ ] Project meeting (from: Mar 10 2026 to: Mar 12 2026)
```

---

### Marking a task as done: `mark`

Marks the specified task as completed.

**Format:**
```
mark INDEX
```

**Example:**
```
mark 1
```

**Expected output:**
```
Nice! I've marked this task as done:
  [T][X] Read CS2103 textbook
```

---

### Unmarking a task: `unmark`

Marks a completed task as not done.

**Format:**
```
unmark INDEX
```

**Example:**
```
unmark 1
```

**Expected output:**
```
OK, I've marked this task as not done yet:
  [T][ ] Read CS2103 textbook
```

---

### Deleting a task: `delete`

Removes a task from your list.

**Format:**
```
delete INDEX
```

**Example:**
```
delete 2
```

**Expected output:**
```
Noted. I've removed this task:
  [D][ ] Submit assignment (by: Mar 1 2026)
Now you have 2 task(s) in the list.
```

---

### Finding tasks: `find`

Searches for tasks whose description contains the given keyword.

**Format:**
```
find KEYWORD
```

**Example:**
```
find meeting
```

**Expected output:**
```
Here are the matching tasks in your list:
1. [E][ ] Project meeting (from: Mar 10 2026 to: Mar 12 2026)
```

---

### Sorting tasks: `sort`

Sorts all tasks alphabetically by description.

**Format:**
```
sort
```

**Expected output:**
```
Done! Your tasks are now sorted alphabetically.
```

---

### Exiting the application: `bye`

Closes the application.

**Format:**
```
bye
```

**Expected output:**
```
Goodbye! Hope to see you again soon!
```

---

## Error Handling

If an invalid command is entered, Bob will display an error message:
```
I'm sorry, I don't understand that command. Try: todo, deadline, event, list, mark, unmark, delete, find, sort, or bye.
```

If required input is missing:
```
The description of a todo cannot be empty. What needs to be done?
```

If a deadline is missing the `/by` part:
```
A deadline must have a /by part! Try: deadline <description> /by <yyyy-MM-dd>
```

If an event is missing `/from` or `/to`:
```
An event must have both /from and /to parts! Try: event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>
```

---

## Command Summary

| Command    | Format                                              | Example                                            |
|------------|-----------------------------------------------------|----------------------------------------------------|
| `todo`     | `todo DESCRIPTION`                                  | `todo Buy milk`                                    |
| `deadline` | `deadline DESCRIPTION /by YYYY-MM-DD`               | `deadline Submit report /by 2026-03-01`            |
| `event`    | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` | `event Conference /from 2026-03-10 /to 2026-03-12` |
| `list`     | `list`                                              | `list`                                             |
| `mark`     | `mark INDEX`                                        | `mark 1`                                           |
| `unmark`   | `unmark INDEX`                                      | `unmark 1`                                         |
| `delete`   | `delete INDEX`                                      | `delete 2`                                         |
| `find`     | `find KEYWORD`                                      | `find meeting`                                     |
| `sort`     | `sort`                                              | `sort`                                             |
| `bye`      | `bye`                                               | `bye`                                              |

---

## Notes

- All dates must be in `YYYY-MM-DD` format (e.g., `2026-03-01`)
- `INDEX` refers to the task number shown in the `list` output (starts from 1)
- Your tasks are automatically saved after every change and restored when you reopen Bob