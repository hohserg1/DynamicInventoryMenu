# DynamicInventoryMenu
Powerful api for create dynamically updated bukkit ui

### Development is continues


## Usage
- add library to you project(you can use jitpack.io for this)

- create menu fabric(partial function)

```Scala
val menu: Player => Menu = Menu.applyOrCreate("Title",new Menu(_, _, size=45))
```

- add buttons and decorations

```Scala
val menu: Player => Menu = new Menu(_, "Title", size=45)
  .addDecoration(new ItemStack(Material.APPLE), 1, 1)
  ```
  
- when you need to open menu

```Scala
menu(player).open()
```

## To do...
- [x] Basic architecture
- [x] List view for OpenHashMap
- [x] List view for any collection
- [ ] Java syntax and collection support
- [ ] Some stuffs



