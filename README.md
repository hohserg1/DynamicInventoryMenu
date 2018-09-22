# DynamicInventoryMenu
Powerful api for create dynamically updated bukkit ui

### Development is contious


## Usage
add library to you project

- create menu fabric(partial function)

```Scala
val menu: Player => Menu = new Menu(_, "Title", size=45)
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
- [ ] List view for any collection
- [ ] Java sintax and collection support
- [ ] Some stuffs



