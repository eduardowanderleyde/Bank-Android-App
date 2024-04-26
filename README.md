# Bank Document

This document outlines the various components and functionalities of the bank application.

Watch Demonstration

[![Watch Demonstration](https://img.youtube.com/vi/35FTJIEv_rg/0.jpg)](https://www.youtube.com/watch?v=35FTJIEv_rg)

## How to Run the Project

To run the project in Android Studio, follow these steps:

1. **Clone the Repository:** Clone the project repository to your local machine using the following command in the terminal:

git clone https://github.com/eduardowanderleyde/Bank-Android-App.git


2. **Open the Project in Android Studio:** Launch Android Studio and open the cloned project by selecting `File > Open` from the menu and navigating to the project directory.

3. **Configure the Emulator or Device:** Set up an Android emulator or connect a physical Android device to your computer. Ensure that the emulator or device meets the requirements specified in the project documentation.

4. **Build and Run the Project:** Once the project is open in Android Studio, click the green "play" button in the toolbar or select `Run > Run 'app'` from the menu. This will compile the project and deploy it to the emulator or connected device.

5. **Interact with the App:** After the app is deployed, interact with it on the emulator or device to test its functionality. Navigate through the app's screens, enter data, and perform various actions as intended.

6. **Debugging and Troubleshooting:** If you encounter any issues while running the app, use the debugging tools provided by Android Studio to identify and fix errors. Check the Logcat output for error messages and stack traces, and refer to the project documentation for troubleshooting guidance.

By following these steps, you should be able to run the project successfully in Android Studio and test its functionality on an emulator or device.

## Explanation

### ContasActivity

In the `ContasActivity`, a thread is utilized to extract information from the database, while a `Runnable` keeps the list constantly updated.

### ContaViewHolder

In this class, the `bindTo` method is modified to receive all parameters of the `Conta` class. Three additional `putExtra` statements are added to send additional information to the `EditarContaActivity` class.

### AdicionarContaActivity

Implemented a `try/catch` block to notify the user of errors if fields are left blank. Additionally, an insertion method is implemented, passing `Conta` as a parameter to the `ContaViewModel` class.

### ContaDAO

Implemented methods for inserting, updating, and deleting accounts. Methods for fetching all accounts and conducting specific searches by name, CPF, and account number are also added. An extra method is implemented to calculate the total balance of the bank.

### ContaRepository

Implemented methods, using the `ContaDao` class, for inserting, updating, and removing accounts from the database. Methods for searching accounts by name, CPF, and account number are also implemented. Additionally, a method to retrieve the total bank balance from the database is added.

### ContaViewModel

Implemented methods, using the `ContaRepository` class, for inserting, updating, removing, and searching for accounts by number.

### EditarContaActivity

Implemented a `try/catch` block to notify the user of errors if fields are left blank. Additionally, methods for updating and removing accounts, passing `Conta` as a parameter to the `ContaViewModel` class, are added.

### BancoViewModel

Implemented methods for transferring, crediting, and debiting using the `Conta` class and corresponding methods from the `ContaRepository` class to save transactions in the database. Methods for searching by name, CPF, and number are also implemented.

### DebitarActivity, CreditarActivity, and TransferirActivity

Implemented a `try/catch` block to notify the user of errors if fields are left blank. In `DebitarActivity`, a value is removed from an account using the `debitar` method of the `BancoViewModel` class. In `CreditarActivity`, a value is added to an account using the `creditar` method. In `TransferirActivity`, a value is removed from one account and added to another using the `transferir` method.

### PesquisarActivity

Implemented methods `buscarPeloNome`, `buscarPeloCPF`, and `buscarPeloNumero` using the `BancoViewModel` class to conduct searches based on user selection.

### MainActivity

Uses a thread to fetch and keep the total bank balance updated, returned by the `getSaldoTotal` method of the `BancoViewModel` class.
