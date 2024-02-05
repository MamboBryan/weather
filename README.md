# Weather

Weather is a simple Kotlin Multiplatform application built with Compose Multiplatform that gets
weather information data for a specific city.

## Features

- [X] user can get today's weather data
- [X] user can get weather data for the next 7 days
- [X] user can get the last 14 days weather data

## Design

### android

|                                    Loading                                    |                                    Today                                    |                                    More                                    |
|:-----------------------------------------------------------------------------:|:---------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|
| <img src="images/android/loading.png" width="200" hspace="2" alt="loading" /> | <img src="images/android/today.png" width="200" hspace="2" alt="calorie" /> | <img src="images/android/more.png" width="200" hspace="2" alt="calorie" /> |

### iOS

|                                  Loading                                  |                                  Today                                  |                                  More                                  |
|:-------------------------------------------------------------------------:|:-----------------------------------------------------------------------:|:----------------------------------------------------------------------:|
| <img src="images/ios/loading.png" width="200" hspace="2" alt="loading" /> | <img src="images/ios/today.png" width="200" hspace="2" alt="calorie" /> | <img src="images/ios/more.png" width="200" hspace="2" alt="calorie" /> |

## Architecture

This project uses the MVI(Model - View - Intent) architecture based on UDF(Unidirectional Data Flow)
and Reactive programming.

Why?

- more clear and intentional separation of concerns
- single source of truth for our UI state which can only be mutated by intent/actions
- simpler and more direct UI testability, since we can define how the UI should look like with our
  state objects

### Packaging Structure

- `sources`
    - `remotesource`
        - handles getting data from any server/remote source
    - `localsource`
        - handles getting cached device data
- `data`
    - handles getting and mutating data from needed sources
- `domain`
    - handles encasing business logic for reuse
- `ui`
    - handles displaying data on device

### Testing

The app includes both unit and instrumented tests.
#### Sources
---

- Remote

|                             WeatherRemoteSource                              |
|:----------------------------------------------------------------------------:|
| <img src="images/testing/remote.png" width="700" hspace="2" alt="loading" /> |

- Local

|             WeatherLocalSource              |
|:-------------------------------------------:|
| <div width="700"> <p>In Progress</p> </div> |

#### Data
---

- Repositories

|                             WeatherRepository                              |
|:--------------------------------------------------------------------------:|
| <img src="images/testing/repo.png" width="700" hspace="2" alt="loading" /> |

- Extensions

|                                         DateTime                                          |
|:-----------------------------------------------------------------------------------------:|
| <img src="images/testing/extensions/datetime.png" width="700" hspace="2" alt="loading" /> |

|                                         String                                          |
|:---------------------------------------------------------------------------------------:|
| <img src="images/testing/extensions/string.png" width="700" hspace="2" alt="loading" /> |

|                                         Int                                          |
|:------------------------------------------------------------------------------------:|
| <img src="images/testing/extensions/int.png" width="700" hspace="2" alt="loading" /> |

#### Domain
---

|                             GetCurrentWeatherDataUseCase                             |
|:------------------------------------------------------------------------------------:|
| <img src="images/testing/domain/current.png" width="700" hspace="2" alt="loading" /> |

|                             GetHistoryWeatherDataUseCase                             |
|:------------------------------------------------------------------------------------:|
| <img src="images/testing/domain/history.png" width="700" hspace="2" alt="loading" /> |

#### ui
---

- screen-model

|                                WeatherDetailScreenModel                                 |
|:---------------------------------------------------------------------------------------:|
| <img src="images/testing/ui/models/details.png" width="700" hspace="2" alt="loading" /> |

|                                WeatherListScreenModel                                |
|:------------------------------------------------------------------------------------:|
| <img src="images/testing/ui/models/list.png" width="700" hspace="2" alt="loading" /> |

- screens

|                                   WeatherDetailScreen                                   |
|:---------------------------------------------------------------------------------------:|
| <img src="images/testing/ui/screens/detail.png" width="700" hspace="2" alt="loading" /> |

|              WeatherListScreen              |
|:-------------------------------------------:|
| <div width="700"> <p>In Progress</p> </div> |

## Stack

### Language & Framework

| Title                                                                              | Description                        |
  |:-----------------------------------------------------------------------------------|:-----------------------------------|
| [Kotlin](https://kotlinlang.org/)                                                  | `fun` programming language         |
| [KMP - Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/)      | cross platform framework           |
| [CMP - Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) | declarative UI rendering framework |
| [Ktor](https://github.com/ktorio/ktor)                                             | networking client framework        |

### Libraries

| Title                                                                    | Description          |
|:-------------------------------------------------------------------------|:---------------------|
| [Kotlinx-DateTime](https://github.com/Kotlin/kotlinx-datetime)           | date/time library    |
| [Kotlinx-Coroutines](https://github.com/Kotlin/kotlinx.coroutines)       | async programming    |
| [Kotlinx-Serialization](https://github.com/Kotlin/kotlinx.serialization) | serialization        |
| [Kamel](https://github.com/Kamel-Media/Kamel)                            | image loading        |
| [Voyager](https://github.com/adrielcafe/voyager)                         | navigation           |
| [Koin](https://github.com/InsertKoinIO/koin)                             | dependency injection |

## Improvements
### Layered
- [ ] Sources
  - [ ] add a local cache for the weather forecast
- [ ] Data
  - [ ] get the current day's weather forecast as a flow
  - [ ] handle getting data from the remote source and caching it on device
- [ ] UI
  - [ ] fix the UX on the dates list screen
### General
- [ ] add Air Quality to the details(IMPORTANT!!!)
- [ ] add setup screen for selecting, country, unit of measurement & language
- [ ] add check to show if country has snow or not
- [ ] add ability change selected data
- [ ] add ability to change time from 24hour to 12 hour