# gameStudio

**My DB is running on port 5433 !!!**

**JDBC Configuration** is in file /psql.conf

**DB schemas** are in file /db.sql

BRANCHES:
---

test-221114 - self-explaining

main - stable working version (currently thymeleaf chapter)

work - not stable, sometimes not even working!

STATUS:
---

Thymeleaf and WEB ui
- ALL games working
- all services working
- login working
- TODO: 
  - register of new user
  - re-work design (partially done)

All WEB games
- fixed bug when player was able to act after game ends

Mines
- working
- left click open, right click (un)mark in web version
 
Tiles
- working

Light
- working

GameStudio
- loading configuration from config file located in root
- extracted service connections to separate class
- preparation for different DB services
- created ALL 3 services
- highscores are working on every game
- Spring runner
- rankings displayed next to each game in main menu

JPA
- separate DB: gamestudiojpa
- all services working
- JPA playground package (user input not implemented yet)



