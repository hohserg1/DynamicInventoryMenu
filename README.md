# DynamicInventoryMenu
Powerful api for create dynamically updated bukkit ui

## Now actual repo is https://github.com/hohserg1/DynamicInventoryMenu

### Development is continues


## Usage
- add library to you project(you can use jitpack.io for this)

- create menu fabric(partial function)

```Scala
val menu: Player => Menu = Menu.applyOrCreate(new Menu(_, "Title", size=45))
```

- add buttons and decorations

```Scala
val menu: Player => Menu = Menu.applyOrCreate(new Menu(_, "Title", size=45)
  .addDecoration(new ItemStack(Material.APPLE), 1, 1))
  ```
  
- when you need to open menu

```Scala
menu(player).open()
```

## To do...
- [x] Basic architecture
- [x] List view for OpenHashMap
- [x] List view for any collection
- [x] Java syntax and collection support
- [ ] Component abstract level
- [ ] Make menus absolutly finalisable(mutable menu to immutable menu)(for force you to conside all usecases on menu creating stage)
- [ ] Some stuffs


