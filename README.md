# CS 501 Individual Assignment 3 Question 3

## Explanation
- Displays a contact list grouped **alphabetically** with **sticky headers** so the current letter stays pinned while scrolling.
- Uses a list of **50 common full names**, grouped and sorted by their first letter.
- A **“Scroll to Top” FAB** appears once you scroll past item 10; tapping it animates back to the top.
- Each section has spacing below it, and headers use a **Surface background** so they don’t overlap with names.

## How to Use
1. Run the app on an emulator or device (or view it with the `@Preview`).
2. Scroll the list; note that headers stay pinned at the top of each section.
3. After scrolling past item 10, tap the **“Scroll to Top”** FAB to jump back smoothly.
4. Notice spacing after each alphabet section and a styled background behind each header.

## Implementation
- Data: `sampleContacts()` generates 50 unique, common names; sorted and grouped by first character.
- UI: `LazyColumn` with `stickyHeader` for each letter section.
- Spacing: `Spacer(height = 12.dp)` adds space after each section.
- Header: `Surface` with `surfaceVariant` background ensures headers don’t overlap text.
- FAB: Visible when `listState.firstVisibleItemIndex > 10`; uses `animateScrollToItem(0)` with coroutine scope.
