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

ScreenShots -> 

<img width="1440" height="900" alt="Screenshot 2025-08-10 at 2 47 45 PM" src="https://github.com/user-attachments/assets/8031993a-3e9a-40e2-bf5f-affa88ab0361" />
<img width="1440" height="900" alt="Screenshot 2025-08-10 at 2 48 24 PM" src="https://github.com/user-attachments/assets/e8235c66-fc95-490d-8df3-f964b5689917" />
<img width="1440" height="900" alt="Screenshot 2025-08-10 at 2 48 43 PM" src="https://github.com/user-attachments/assets/4303b4a4-1fb2-44a1-a328-a7308db76849" />

AI Usage Summary ;-

Used AI to scaffold MVVM fragments, navigation, and repo.

Iterated on validation, date handling, and CSV share flow.
