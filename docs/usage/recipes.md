# Crafting Recipes

All crafting recipes for Steve's Simple Storage. Recipes use the standard Minecraft 3×3 shaped crafting grid.

## Storage Blocks

### Storage Core

**Output:** 1× Storage Core

```
I I I
I C I
I I I

I = Iron Ingot
C = Chest
```

### Storage Box

**Output:** 1× Storage Box

```
W W W
W C W
W W W

W = Any Planks
C = Chest
```

### Condensed Storage Box

**Output:** 1× Condensed Storage Box

```
C C C
C S C
C C C

C = Cobblestone
S = Storage Box
```

### Compressed Storage Box

**Output:** 1× Compressed Storage Box

```
C C C
C S C
C C C

C = Copper Ingot
S = Condensed Storage Box
```

### Super Storage Box

**Output:** 1× Super Storage Box

```
I I I
I S I
I I I

I = Iron Ingot
S = Compressed Storage Box
```

### Ultra Storage Box

**Output:** 1× Ultra Storage Box

```
G G G
G S G
G G G

G = Gold Ingot
S = Super Storage Box
```

### Hyper Storage Box

**Output:** 1× Hyper Storage Box

```
D D D
D S D
D D D

D = Diamond
S = Ultra Storage Box
```

### Ultimate Storage Box

**Output:** 1× Ultimate Storage Box

```
D D D
N S N
D D D

D = Diamond
N = Netherite Ingot
S = Hyper Storage Box
```

## Feature Blocks

### Crafting Box

**Output:** 1× Crafting Box

```
E D E
C B C
C E C

E = Ender Eye
D = Diamond
C = Crafting Table
B = Storage Box
```

### Search Box

**Output:** 1× Search Box

```
E D E
C B C
C E C

E = Enchanted Book
D = Compass
C = Iron Block
B = Storage Box
```

### Sort Box

**Output:** 1× Sort Box

```
E D E
C B C
C E C

E = Enchanted Book
D = Comparator
C = Iron Block
B = Storage Box
```

### Statistics Box

**Output:** 1× Statistics Box

```
I G I
G R G
I B I

I = Iron Ingot
G = Glass Pane
R = Redstone
B = Blank Box
```

### Security Box

**Output:** 1× Security Box

```
G K G
G B G
G G G

G = Gold Block
K = Key
B = Storage Box
```

### Access Terminal

**Output:** 1× Access Terminal

```
I X I
X A X
I X I

X = Glass Pane
I = Iron Bars
A = Storage Core
```

### Blank Box

**Output:** 4× Blank Box

```
P   P
P   P
P   P

P = Any Planks
```

## Automation Blocks

### Input Port

**Output:** 1× Input Port

```
  H
I B I
  I

H = Hopper
I = Iron Ingot
B = Storage Box
```

### Extract Port

**Output:** 1× Extract Port

```
  I
I B I
  H

H = Hopper
I = Iron Ingot
B = Storage Box
```

### Eject Port

**Output:** 1× Eject Port

```
  P
I B I
  I

P = Piston
I = Iron Ingot
B = Storage Box
```

### Storage Interface

**Output:** 1× Storage Interface

```
  I
E P I
  I

I = Iron Ingot
E = Extract Port
P = Input Port
```

## Items

### Key

**Output:** 1× Key

```
  I
I G I
  I

I = Iron Ingot
G = Gold Ingot
```

### Dolly

**Output:** 1× Dolly

```
S I S
I W I
S I S

S = Stick
I = Iron Ingot
W = Any Planks
```

### Super Dolly

**Output:** 1× Super Dolly

```
D D D
D G D
D D D

D = Diamond
G = Dolly
```

## Recipe Configuration

Some recipe variants are planned but not yet implemented:

| Config Option    | Default | Description         |
|------------------|---------|---------------------|
| `classicRecipes` | `false` | Not yet implemented |
| `toughHyper`     | `false` | Not yet implemented |

These options appear in `config/s3-common.toml` but do not currently change the available recipes.
