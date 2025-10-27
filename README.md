**Brief Explanation**

This project demonstrates a typical Android MVVM (Model-View-ViewModel) app using Clean Architecture, Hilt for dependency injection, Retrofit for networking, and proper separation of concerns throughout the layers.
The solution focuses on maintainability, clarity, and ease of testing.

**Explanation of Logic**

**Truecaller15thCharacterRequest :**

This class is responsible for extracting the 15th character from a given text input. It assumes the input is a continuous plain text and returns the character at position 15 (index 14, zero-based). This simple task highlights targeted character access for display or processing.

**TruecallerEvery15thCharacterRequest :**

This class extracts every 15th character from the input text, meaning it collects characters at positions 15, 30, 45, and so on. The output is a list or array of these characters. This task demonstrates how to systematically sample characters across a long text string in fixed intervals.

**TruecallerWordCounterRequest :**

This class processes the entire input text by splitting it into words separated by whitespace characters (spaces, tabs, line breaks, etc.). It then counts the occurrences of each unique word case-insensitively and produces a mapping of words to their frequency counts. This task is useful for analyzing text content to understand word distribution or for indexing.

**Key implementation**

**MVVM Structure:**
The app strictly separates UI (Activity), ViewModel, Repository, and UseCase/business logic layers to maintain testability and modularity.

**Dependency Injection:**
Hilt is used to inject dependencies across all layers, reducing boilerplate and enabling easy swapping or mocking of dependencies in tests.

**Networking:**
Retrofit is used with a simple scalar converter for text-based API responses, assuming the remote server is reliable and responses are processed as plain text.

**Result Handling:** 
Loading, success, and error states are represented using a sealed class (Resource<T>), which simplifies UI state handling and is considered a best practice for modern architecture.

**Unit Testing:**
All core logic (UseCases, Repository, ViewModel) is covered by unit tests. The tests use the kotlinx-coroutines-test library for coroutine handling and custom utilities to reliably await LiveData emissions.
Assumption: Testing is focused on business logic and state emission, rather than Android UI or integration tests.

**UI:**

UI demonstrates standard TextView/Button/ProgressBar widgets; no custom UI or extensive error checks.

The app assumes a happy path for the API (i.e., server always sends valid data structure).

Boilerplate and repetitive code (like exhaustive input validation and edge-case error handling) is minimized to focus on the app’s main flow and demonstrate patterns.

All network operations, loading states, and error outcomes are handled at the ViewModel level without a separate UI state class for each task for brevity.

