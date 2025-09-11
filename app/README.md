# Cheese Factory 🧀

A comprehensive Android application for cheese enthusiasts to explore, discover, and manage their favorite cheese varieties. Built with modern Android development practices using Kotlin and MVVM architecture.

## Features

### 🔍 **Cheese Catalog**
- Browse through a curated collection of 20+ premium cheese varieties
- High-quality cheese images with detailed information
- Search functionality to quickly find specific cheeses
- Advanced filtering system by:
    - Milk source (Cow, Buffalo, Sheep, Goat, Mixed)
    - Texture (Soft, Semi-soft, Semi-hard, Hard, Crumbly, Spreadable)
    - Flavor profile (Mild, Buttery, Nutty, Tangy, Sharp, Pungent, Sweet)
    - Aging period (Fresh, Young, Matured, Aged, Extra Aged)

### ❤️ **Favorites Management**
- Add/remove cheeses to your personal favorites collection
- Dedicated favorites tab for quick access to preferred cheeses
- Visual feedback with like/unlike functionality

### 📱 **Detailed Information**
- Comprehensive cheese profiles including:
    - Origin and history
    - Milk source and production details
    - Texture and aging information
    - Flavor characteristics
    - Food pairing suggestions
    - Wine pairing recommendations

### 🎨 **Modern UI/UX**
- Clean, intuitive Material Design interface
- Smooth navigation with fragment-based architecture
- Responsive card-based layout
- Interactive filtering with expandable chip groups
- Toast notifications for user feedback

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: Android Views with Material Design Components
- **Navigation**: Fragment-based navigation with FragmentManager
- **Data Management**: LiveData and ViewModel
- **Layout**: RecyclerView with CardView items
- **Search**: SearchView with real-time filtering
- **Filtering**: Material Chip Groups with multi-selection

## Project Structure

```
com.example.cheesefactory/
├── CheeseData.kt              # Data model for cheese objects
├── CheeseViewModel.kt         # ViewModel managing app state and business logic
├── CheeseAdapter.kt           # RecyclerView adapter for cheese list
├── MainActivity.kt            # Main activity with tab navigation
├── CatalogueCheeseFrag.kt     # Fragment for cheese catalog and filtering
├── FavouriteCheeseFrag.kt     # Fragment for favorite cheeses
└── DetailCheese.kt            # Fragment for detailed cheese information
```

## Architecture Overview

### MVVM Pattern Implementation

The app follows the Model-View-ViewModel architecture pattern:

- **Model**: `CheeseData` - Represents cheese objects with all properties
- **View**: Fragments and Activities - Handle UI interactions and display
- **ViewModel**: `CheeseViewModel` - Manages UI-related data and business logic

### Data Flow

1. **ViewModel** holds and manages all application state using `LiveData`
2. **Fragments** observe `LiveData` changes and update UI accordingly
3. **User interactions** trigger ViewModel methods that update the state
4. **Filtering and search** operations are handled through ViewModel methods

## Key Components

### CheeseViewModel
- Manages cheese list, favorites, and selected cheese state
- Handles filtering logic with multiple criteria
- Provides methods for adding/removing favorites
- Uses `MutableLiveData` for reactive UI updates

### CheeseAdapter
- RecyclerView adapter with ViewHolder pattern
- Handles item clicks and favorite button interactions
- Supports dynamic list updates for filtering and search

### Fragment Navigation
- Uses FragmentManager for navigation between screens
- Implements hide/show pattern for main tabs
- Manages back stack for detail views

## Setup and Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API level 21+ (Android 5.0)
- Kotlin 1.5+

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/cheese-factory.git
   cd cheese-factory
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an existing Android Studio project"
    - Navigate to the project directory and select it

3. **Sync dependencies**
    - Android Studio will automatically sync Gradle dependencies
    - Wait for the sync to complete

4. **Add cheese resources**
    - Ensure all cheese images are placed in `res/drawable/`
    - Verify string arrays are properly defined in `res/values/arrays.xml`

5. **Run the application**
    - Connect an Android device or start an emulator
    - Click "Run" or use `Shift + F10`

## Resource Requirements

### Images
The app requires 20 cheese images named according to the cheese types:
- `brie.jpg`, `burrata.jpg`, `cabrales.jpg`, `camembert.jpg`
- `cheddar.jpg`, `chevre.jpg`, `colby.jpg`, `cottage.jpg`
- `emmental.jpg`, `garrotxa.jpg`, `gouda.jpg`, `gru.jpg`
- `havarti.jpg`, `manchego.jpg`, `mascarpone.jpg`
- `mozzarella_di_bufala.jpg`, `mozzarella.jpg`
- `parmesan.jpg`, `pecorino_romano.jpg`, `roquefort.jpg`

### String Arrays
Required string arrays in `res/values/arrays.xml`:
- `cheese_names` - Cheese names
- `cheese_short_desc` - Brief descriptions
- `cheese_descriptions` - Detailed descriptions
- `cheese_origins` - Origin information
- `cheese_milk_source` - Milk sources
- `cheese_texture` - Texture types
- `cheese_aging` - Aging information
- `cheese_flavor` - Flavor profiles
- `food_pairings` - Food pairing suggestions
- `wine_pairings` - Wine pairing recommendations

## Usage

### Browsing Cheeses
1. Launch the app to view the main catalog
2. Scroll through the cheese collection
3. Use the search bar to find specific cheeses
4. Tap the filter button to access advanced filtering options

### Filtering
1. Tap the filter icon to expand filter options
2. Select desired criteria from expandable chip groups
3. Tap "Submit" to apply filters
4. Clear filters by deselecting all chips and resubmitting

### Managing Favorites
1. Tap the heart icon on any cheese to add/remove from favorites
2. Switch to the "Favorites" tab to view saved cheeses
3. Favorites persist during the app session

### Viewing Details
1. Tap on any cheese card to view detailed information
2. Access comprehensive details including pairings and history
3. Use the back button to return to the previous screen

## Acknowledgments

- Cheese information sourced from various culinary databases
- Icons from Material Design Icons
- Images from various cheese producers and culinary websites

**Made with ❤️ for cheese lovers everywhere**