# DuoDays
> A simple app to input and display content on a feed

[![Build Status](https://travis-ci.com/markthomasstevenson/DuoDays.svg?branch=master)](https://travis-ci.com/markthomasstevenson/DuoDays)

## Feature List
### Content Screen in a scrolling feed
    - Has empty state
    - Shows data from DB
### Add Content Screen with 2 fields
    - Error handling
    - Keyboard handling
    - Saves to backend DB
    - Arbitrary limit of characters for inputs
### Content shown in Cards
    - Shows entered text on visibly distinct cards
    - Allows deletion of card/DB entry

## Missing Features
- Editing of card/DB entry
    - This would be done by reusing the Add Content Screen
- Swipe to delete on cards
    - Just to increase ease or UX
- Delete All action on Home Screen
    - For ease of use and convienience

## Tech
- Written in Kotlin using Android Jetpack utilities (Nav, Live data, Viewmodels, etc)
- Database using Realm
- Testing using combination of Powermock and Roboelectric
