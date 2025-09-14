# Budget Tracker Android Application

A personal finance management application built with modern Android development practices using Jetpack Compose and Kotlin.

## 📱 Features

### Transaction Management
- **Add Transactions**: Record financial transactions with amount, description, date, and category
- **Category Organization**: Organize expenses into predefined categories (Groceries, Rent, Utilities, Transport, Entertainment, Others)
- **Transaction History**: View all transactions in a scrollable list with complete details
- **Delete Functionality**: Remove unwanted transactions with balance adjustment
- **Balance Tracking**: Real-time balance updates with manual adjustment capability

### Data Visualization
- **Interactive Pie Chart**: Custom-built pie chart using Canvas API showing spending breakdown by category
- **Percentage Analysis**: Automatic calculation and display of spending percentages per category
- **Color-Coded Categories**: Each category assigned unique colors for easy identification
- **Total Spending Summary**: Comprehensive overview of total expenditure

### Goal Setting
- **Personal Goals**: Set and track financial savings goals
- **Goal Management**: Add and delete financial objectives
- **Sidebar Interface**: Dedicated sidebar for managing goals without disrupting main workflow

### Modern UI/UX
- **Material Design 3**: Implements latest Material Design guidelines
- **Responsive Layout**: Optimized for various screen sizes
- **Intuitive Navigation**: Seamless navigation between transaction and analysis screens
- **Interactive Elements**: Floating action buttons and modal dialogs for enhanced user experience

## 🏗️ Architecture

### MVVM Pattern
- **ViewModels**: `TransactionViewModel` and `GoalViewModel` for state management
- **StateFlow**: Reactive programming with StateFlow for UI updates
- **Separation of Concerns**: Clear separation between UI, business logic, and data layers

### Database Layer
- **SQLite Integration**: Local database for persistent data storage
- **Database Helper**: Custom `MyDatabaseHelper` for database operations
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality
- **Data Models**: Well-structured data classes for Transaction and Goal entities

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: SQLite with custom helper class
- **Reactive Programming**: StateFlow and Compose State
- **Navigation**: Jetpack Navigation Compose
- **Custom Graphics**: Canvas API for pie chart visualization
- **Material Design**: Material Design 3 components

## 📁 Project Structure

```
com.example.assignment1/
├── db/
│   └── MyDatabaseHelper.kt          # Database operations
├── model/
│   ├── Transaction.kt               # Transaction data class
│   └── Goal.kt                      # Goal data class
├── ui/
│   ├── TransactionScreen.kt         # Main transaction interface
│   ├── AnalysisScreen.kt           # Data visualization screen
│   └── components/
│       ├── GoalsSidebar.kt         # Goals management sidebar
│       └── ThreeFieldInputDialog.kt # Transaction input dialog
├── ui/theme/
│   ├── Color.kt                    # Color definitions
│   ├── Theme.kt                    # Material theme setup
│   └── Type.kt                     # Typography definitions
├── viewmodel/
│   ├── TransactionViewModel.kt     # Transaction state management
│   └── GoalViewModel.kt           # Goal state management
└── MainActivity.kt                 # Main activity with navigation
```

## 🚀 Key Features Implementation

### Custom Pie Chart
- Built from scratch using Jetpack Compose Canvas API
- Dynamic color assignment based on category
- Proportional arc calculation based on spending amounts
- White borders for visual separation

### Database Management
- Robust SQLite implementation with proper error handling
- Auto-increment IDs for both transactions and goals
- Date formatting and validation
- Transaction restoration on deletion for accurate balance tracking

### State Management
- Reactive UI updates using StateFlow
- Proper state hoisting in Compose
- Memory-efficient state management without browser storage dependencies

### User Experience
- Intuitive floating action buttons for primary actions
- Modal dialogs for data input with validation
- Real-time balance updates
- Smooth navigation between screens

## 📊 Data Visualization

The analysis screen features a comprehensive breakdown of spending patterns:
- **Pie Chart Visualization**: Visual representation of spending by category
- **Category Breakdown List**: Detailed list with amounts and percentages
- **Total Spending Summary**: Complete financial overview
- **Empty State Handling**: Graceful handling when no transactions exist

## 🎯 Goals Feature

Personal financial goal tracking system:
- **Goal Creation**: Set financial objectives with descriptions and target amounts
- **Goal Management**: View, add, and delete goals through sidebar interface
- **Persistent Storage**: Goals saved in local SQLite database
- **Clean UI**: Dedicated interface that doesn't interfere with main application flow

## 🔧 Installation & Setup

1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Run on Android device or emulator (API level 21+)

## 📱 Compatibility

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: Latest Android API
- **Architecture**: All Android architectures supported

## 🏆 Development Highlights

- **Clean Architecture**: Well-structured codebase following Android best practices
- **Modern Android Development**: Utilizes latest Android development tools and libraries
- **Custom Components**: Hand-built UI components for specific functionality
- **Performance Optimized**: Efficient state management and database operations
- **User-Centered Design**: Intuitive interface focusing on user experience

This project demonstrates proficiency in modern Android development, including Jetpack Compose, MVVM architecture, local database management, custom UI components, and data visualization techniques.
