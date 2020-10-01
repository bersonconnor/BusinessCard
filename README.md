# BusinessCard
Asymmetrik Business Card Scanner Challenge Problem

# Table of Contents
- [Project Setup](#ProjectSetup)
- [Necessary Software](#Software)
- [Usage](#Usage)
  - [Main Menu](##MainMenu)
  - [Manual Input](##Manual)
  - [Provide Text File](##TextFile)
  - [Run Examples](##Example)
 - [Folder Structure](#FolderStructure)

# Necessary Software
- JDK and a java compiler 
- This application builds from a Bash-compatible console

# Project Setup
How to download, setup, and run the project
- Run `git clone <url>` where `<url>` is the url of the GitHub project
- Navigate to the [BusinessCard/src](https://github.com/bersonconnor/BusinessCard/tree/master/src) directory in the command line
- Run ```shell sh run.sh``` to compile all java files and run the command line application

# Usage
How to use the command line application 
## Main Menu
- Enter 1, 2, or 3 to manually input business card text, provide a business card text file, or run examples.
- Enter 4 or exit to exit the command line application
## Manual Input
- Enter each line of the business card text separated by a new line
- Enter **done** and then press enter once you have finished entering the business card text
- After the results are printed, the program will return to the [Main Menu](##MainMenu)
## Provide Text File
- Enter the **full path** of the text file that you would like to parse
- Enter exit to return to the [Main Menu](##MainMenu)
## Run Examples
- Enter any number between and including the provided minimum and maximum example numbers
- Enter exit to return to the [Main Menu](##MainMenu)

# Folder Structure
## [src](https://github.com/bersonconnor/BusinessCard/tree/master/src)
- Contains all code related to the application
- Contains [run.sh](https://github.com/bersonconnor/BusinessCard/tree/master/src/run.sh) for setting up the project
- Contains [example](https://github.com/bersonconnor/BusinessCard/tree/master/src/example) folder that contains compatible business card text files
## [doc](https://github.com/bersonconnor/BusinessCard/tree/master/doc)
- Contains [javadoc](https://github.com/bersonconnor/BusinessCard/tree/master/doc/javadoc) folder
  - Javadocs that end with **\_private** include all methods and fields 
  - Javadocs named directly after their corresponding java file only contain public methods and fields
- The [README.md](https://github.com/bersonconnor/BusinessCard/tree/master/doc/javadoc/README.md) file in this folder provides all other documentation


