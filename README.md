A simple daily expense tracker built with XML UI + MVVM (StateFlow), Navigation, and an in‑memory repository. It fulfills the Smart Daily Expense Tracker assignment: Entry, List (by date), Report (last 7 days + category totals), validation, image pick, and CSV share.

✨ Features

Add Expense: title, amount (₹), category (Staff/Travel/Food/Utility), optional notes (≤100 chars), optional receipt image.

Today’s total on the entry screen.

List by Date: pick any date to see expenses + total.

Report: mocked last 7 days totals and category totals.

Share CSV from the report screen (via FileProvider).

MVVM with a shared ExpenseViewModel using StateFlow.

-> Tech & Libraries

Kotlin + Coroutines/StateFlow

AndroidX Navigation (fragments + action bar integration)

Material Components (Toolbar)

RecyclerView

