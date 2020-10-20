Welcome to the lake Lanier.
This is a texbased game which allows the player to navigate through lake lanier.
The player is able to do multiple activities in this game by navigating through these rooms.
Each room has a unique ID called roomId
This game keeps track of the player so if the player visits a room for a second time the message "Looks familiar. You already been here" will be displayed to the player.
Player is allowed to navigate north, south, east, west by the following commands. N, S, E, W. these commands need to be typed in uppercase in order to work.
After entering a room, the description of the room is going to be displayed to the player.

******************** IMPORTANT *******************
the way i designed my puzzle in this game is different.
every puzzle is the key in order to enter the room which the puzzle exists in. Therefore, if the player does not answer the question currently,
 he/she will not be able to enter the room. In order to enter a room which contains a puzzle, player must answer the puzzle currently.

******************* Commands *************************
<N>
allows the player to move north

<S>
allows the player to move south

<E>
allows the player to move east

<W>
allows the player to move west

<pickup Item's name>
allows the player to pickup an item

<inspect item's name>
allows the player to inspect an items description

<drop item's name>
allows the player to drop an item

<inventory>
displays the items that are in the player's inventory

**************** txt files explanation *********************
<items.txt>
this file contains the information about the items, such as, the name, description, pickup.
name is simply the item's name
description is a short description about the item
pick up is the message that must be displayed when the player picks up an item.

<rooms.txt>
this file contains the information about the rooms, such as, id, name, description, direction.
id is the room's id which must be unique.
name is the room's name
description is a short description about the room
direction has the directions available from a room to the next possible rooms

<puzzles.txt>
this file contains the information about the puzzles, such as, question, answer
question is the question which must be displayed when the player is trying to enter a room that contains a puzzle
answer contains the answer to the question

***************** Enjoy the lake Lanier ********************
*** FOR THE UNGRADED VERSION OF MAP, OPEN THE Map_Image.png ***

                            _______
                           |       |
                           |   1   |
                           |_______|
                               *
                               *
                            _______
                           |       |
                           |   2   |
                           |_______|
                              *
                              *
                              *
 ______       _______      _______
|      |      |     |      |     |
|  5   |******|  4  |******|  3  |
|______|      |_____|      |_____|
                *
                *
             _______
            |       |
            |   6   |
            |_______|
