# wishiu

<img src="https://github.com/miguelssimao/wishiu/blob/main/app/src/main/res/drawable/ic_launcher_round.png" align="left" height="72"/>
The <b>wishiu</b> is an Android application that resulted from my University project for the Mobile Application Development course, back in 2019. This is a simple wishlist app that can also help you track your savings so you can ultimately be able to purchase the items in your list.

<br clear="left"/>
<br clear="left"/>

This application makes use of [sqlite](https://github.com/sqlite/sqlite) for an embedded database, and many UI components such as Fragments, RecyclerViews controlled by ListAdapters, among others.

---

<p align="center">
  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/1.jpg" width="30%" />
  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/2.jpg" width="30%" />
  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/3.jpg" width="30%" />

  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/4.jpg" width="30%" />
  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/5.jpg" width="30%" />
  <img src="https://github.com/miguelssimao/wishiu/blob/main/screens/6.jpg" width="30%" />
</p>

## Features

| Features | Description |
| -------- | ----------- |
| **Categories** | you can view the current items in your wishlist (`Wishes`), items whose values have already been reached (`Achieved`) and achieved items that have a delivery date (`Scheduled`) |
| **New items** | you can add a picture, category, title, price and starting value to each new item |
| **Savings** | you can have individual savings vaults for each item on your list and keep track of your progress |
| **Progress** | the progress bar shows how much of your goal has already been reached and it is updated everytime you add or remove savings from an item's page |
| **Searching** | clicking the `Search for this item` button will open a Google search page specific to the item's title |
| **Achievements** | you can set an item as `Achieved` once you have saved enough to reach its value or if you have already purchased it |
| **Tracking** | you can set an achieved item as `Scheduled` after a delivery date has been added, and keep track of how much time is left for it to arrive |
| **Settings** | you can set your name (or nickname) in the `Settings` page |
