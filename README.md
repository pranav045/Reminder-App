# ⏰ Reminder App (Compose + Kotlin)

A lightweight **Reminder App** built with **Jetpack Compose** and **Kotlin**, focused purely on scheduling reminders using Android’s native scheduling APIs.  
No database or persistent storage — just quick scheduling and notifications.

---

## 🚀 Features
- Create reminders with title, date, and time
- Schedule notifications using **AlarmManager**
- Cancel or reschedule reminders
- Clean and modern UI with Jetpack Compose
- Minimal, lightweight codebase

---

## 🛠️ Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Scheduling:** AlarmManager + PendingIntent
- **Notifications:** NotificationCompat

---

## 📂 Project Architecture

| File                | Description                                                                 |
|---------------------|-----------------------------------------------------------------------------|
| `MainActivity.kt`   | Activity with Compose UI to schedule alarms using **AlarmManager**          |
| `AlarmReceiver.kt`  | BroadcastReceiver that shows a notification when the scheduled alarm fires
---

## ⚙️ Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/reminder-app.git
