# TorsGarden-Pokitto
A garden simulator game for Pokitto.

## Gameplay
Player begins with no `coin`, a single `seed`, and a single dead plant. The introduction text can explain this ("all your uncle left you was this single dead plant. time to clear it away and start over!" or similar), but the player should be left to till/plant their seed wherever they like.

The first plant, if pests will attack it, should never be killed by pests. The player should be able to get `coin` on their first go unless we want some sort of Ironman Mode.

Untilled earth cannot be sown, and tilled earth will eventually revert to grass if nothing's planted in it. In this way, the player can decide the layout of their garden. The garden can be walled in with trees (unharvestable), fence, or other impassable barrier.

Once some plants are in the ground, the core gameplay loop consists of regular watering (not sure how to make this cyclical yet without a day/night cycle), pest control, then eventual harvest.

Once a harvestable plant has been harvested, it will need to be cleared to make room for a new plant. For simplicity, we'll leave out perennials and other perpetual types. Differing types of plant will have different duration of growth, water needs (if not using some cycle that would encourage regular watering of all plants).

Decorative plants aren't harvestable, but can be cleared if the player desires. If the plant is removed, the land under them is regular land that will need to be re-tilled. Decorative plants will also have a longer life cycle so the player isn't constantly re-planting them.

Decorative seeds should be pricier than harvestable seeds to give the player a money sink, as well as the end goal of customizing and decorating their garden.

## Core Gameplay Loop
Player plants, waters, and harvests plants to gain items for sale. Selling items gives coin to buy more seeds, upgrade (to tools), or expand their land to grow more crops at a time.

## Winning:
The idea is to just keep playing. But ideally once all land is unlocked, all tools upgraded fully, and coin is maxed out (if possible) then that is pretty much end game (potential for an award like an on screen trophy), but the end goal is the garden itself.

## Display
Not really determined yet: smooth scrolling, or individual screens?

## Saving
Since the goal is customization of the garden, the garden's state should persist. We can do this with an explicit save action, or as part of whatever cycle is implemented.

## Tools
|Tool|Use|
|----|----|
|Hoe |Tills earth and clears dead plants for planting new seeds|
|Seed|Seeds are purchased and sometimes dropped by harvesting plants. They are used to plant more plants|
|Watering Can|For watering plants|
|----|----|

## Plant Growth Stages
|Stage|notes|
|----|----|
|Seed|Doesn't need pest control, but requires water or it will die|
|Plant1|Just a sprout, but now requires pest control as well as water|
|Plant2|Leaves form, attracting more pests|
|Plant3|The harvestable crops begin to form|
|Harvest|The plant is ready to be harvested|

