# ComposeChat

## Stack
- Kotlin
- Compose
- MVVM
- Hilt
- Room
- Coroutines
- Flow

## Approach
- Compose was used for UI layer. 
- For Dependency Injecion, Hilt was used. The general patern is MVVM based, with DataSource/DAO, Repository, ViewModel and View layers and the communication is done 
    thru an Observer pattern or rather Flow library for Android. 
- In the set up architecture the DataSource layer exposes storage/network operation, Repository layer is an intermediary layer where data can be mapped/changed in
    both directions, Viewmodel triggers and observes data (also holds some data and calculations for View related operations) and the View only reacts to data changes.
- Project is organized in core package and features package. core contains shared code, features contain different app features - 
    conversation in our example. Packages under conversation package are organized by layers.

## What can be done better?
- Some message related sending events - like posting a message to a in memory list until it's sent and show some UI for 
      different message states (sent, seen, sending, etc...). 
- Dot type animation for replies. 
- Error handling. 
- Prolly some bug fixes that I've missed. 
- Extract resources (dimens).

## Video


https://user-images.githubusercontent.com/42001876/145730545-2f4e1657-9491-4d55-a80a-660a6659e02b.mp4


