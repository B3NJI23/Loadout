<h1>Loadout</h1>

<h2>Description</h2>

<p>Loadout is an android app to help calculate expenses, which deduct directly from inputted real income. Fast, easy, straighforward. No Telemetry, No Ads, No AI</p>

## What it does right now

1. **Income page**: type your monthly bruttó salary and see your nettó, using Hungarian deductions (TB járulék 18.5%, SZJA 15%, optional under-25 and newlywed allowances).
2. **Budget page**: your nettó sits at the top with a bar that shrinks as you put money into expenses (rent, közös költség, utilities, groceries, transport, fun, savings, or your own).

## Running it

**On your PC:** install [Android Studio](https://developer.android.com/studio), choose *Open*, pick this folder, wait for Gradle to sync, then press the green ▶ Run button (with an emulator or your phone plugged in with USB debugging on).

**Straight to your phone:** every push builds an APK on GitHub. Open the *Actions* tab, click the latest *Android build* run, download `loadout-debug-apk` and install it (Android will ask you to allow installs from that source).

## Where things live

```
app/src/main/java/com/b3nji/loadout/
├── MainActivity.kt          app entry point, switches between the two pages
├── LoadoutViewModel.kt      all app state (salary, expenses) and the actions that change it
├── data/
│   ├── HungarianSalary.kt   bruttó → nettó maths
│   └── Expense.kt           expense model + the default list
├── ui/
│   ├── theme/Theme.kt       colours (#3b3b3b, #7060f7) and rounded shapes
│   ├── components/          the shared rounded card
│   ├── income/              income page
│   └── budget/              budget page
└── util/Money.kt            "1 234 567 Ft" formatting
```

Unit tests for the salary maths are in `app/src/test`. Run them with `./gradlew testDebugUnitTest`.
