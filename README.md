# BlockDefend
Spigot plugin for minecraft. Prevents player from doing specific things on blocks/players.

### Supported features

* Blocks place prevention
* Blocks break prevention
* Blocks interact prevention
* Entity damage prevention

### Commands

* `addsafeplace` - prevents place
* `addsafebreak` - prevents break
* `addsafeinteract` - prevents interact
* `addsafeattack` - prevents damage
* `addsafethree` - prevents place, break and attack
* `addsafeall` - prevents place, break, attack and damage

Every command is supplied by 6 arguments: x1, y1, z1 and x2, y2, z2, where `x1, y1, z1` is minimal xyz coordinates and `x2, y2, z2` is maximal xyz coordinates, that form safe box.
