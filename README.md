# MindBoard

## Features

    - Create, edit, and delete idea cards
    - Add images to ideas
    - Search and filter ideas
    - Local data storage (Room / DataStore)
    - Fast and smooth UI powered by Jetpack Compose

---

## Stack

**UI / Frontend** - Jetpack Compose
- Material 3
- Compose Navigation
- Liquid (https://fletchmckee.github.io/liquid/)

**Architecture** - MVVM
- ViewModel

**Data** - Room Database
- DataStore Preferences

---

## Project Structure

    app/
     ├── data/
     │    ├── repository/IdeaRepository.kt
     │    └── models/Idea.kt
     ├── extensions/toDp.tk
     ├── navigation/AppNavHost.kt
     ├── ui/
     │    ├── screens
     │    │     ├── AddIdeaScreen.kt
     │    │     └── HomeScreen.kt
     │    └── widgets/
     ├── viewmodel/IdeaViewModel.kt
     └── MainActivity.kt

------------------------------------------------------------------------

## Getting Started

1. Install **Android Studio**
2. Clone the repository:

``` bash
git clone https://github.com/1nteractme/Mindboard-Kotlin.git
```

3. Open the project in Android Studio
4. Let Gradle sync
5. Run the app on a device or emulator

------------------------------------------------------------------------

## Roadmap

- Edit and delete idea cards 
- Add images to ideas
- Search and filter ideas 
- Cloud sync 
- Firebase integration 
- AI-generated ideas 
- Collections & groups