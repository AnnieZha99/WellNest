# WellNest 1.0

Healthy living areas exploration based on air quality levels and health inventories with BenMap.

## About

WellNest is a mobile application that translates intangible health risks—like pollution, noise, or hazards—into tangible monetary equivalents, aiding more informed decisions for prospective home buyers, real estate agents, and relocation advisors.

## Technology Stack

- **OS Supported**: Android
- **Frontend**: Kotlin
- **Backend**: Python, FastAPI, MariaDB, Firebase
- **APIs**:
  - AERMOD
  - BenMap
  - Google Map APIs (Google Cloud)
- **Database**: Firebase/SQLite

## Key Features & Differentiations

### Comparative Analysis
Quickly compare multiple properties on environmental health risks and related cost.

### Dynamic Data Updates
Continuously refreshes housing and health-related data.

### Customization
Users set their health risk priorities.

### Visual Insights
Interactive maps and clear data visualizations drive better understanding.

## Project Goals

### Goal 1 (Sept-Nov)
Seamlessly fetch and display introductory pollution metrics from external APIs on the map view.

### Goal 2 (Jan-March)
Fully integrate specialized APIs and display comprehensive datasets, present within a clear, user-friendly Result View interface.

### Goal 3 (March-May)
Expand the app's data sources to encompass a wider range of health, environmental, and economic metrics and enhance personalization.

## Target Audience
Prospective home buyers, real estate agents, and relocation advisors.

## Value Proposition
WellNest translates intangible health risks into tangible monetary equivalents, aiding more informed decisions.

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Add your Google Maps API key in the app/build.gradle.kts file
4. Create a Firebase project and add the google-services.json file to the app directory
5. Build and run the project

## Development Guidelines

- Follow the MVVM architecture pattern
- Use Kotlin coroutines for asynchronous operations
- Use Firebase for authentication and data storage
- Use Retrofit for API calls
- Use Material Design components for UI 