# Hypixel Pit Plugin

A complete Java 8 + Spigot 1.8.8 plugin scaffold for a Pit-inspired mechanics system, built to match the progression structure you described:

- Leveling and prestige progression
- Gold, XP, and Renown economy
- Streak and megastreak state tracking
- Passive upgrade basics
- Perk slot scaffolding
- Renown shop pricing
- Mystic item life framework
- Commands for stats, prestige, help, Renown shop, passives, and Mystic status

## Build

```bash
mvn clean package
```

This produces a `target/` plugin JAR.

## Install

1. Place the generated JAR into your Spigot server's `plugins/` directory.
2. Start the server.
3. Verify the plugin loads.

## Commands

```text
/pitstats [player]
/prestige
/pithelp
/renownshop
/passive
/mystic
```

## Notes

- Targeted for Java 8 and Spigot 1.8.8 for canonical PvP compatibility.
- This project is designed as a solid mechanics foundation for later gameplay content like boss events, contracts, faction alignment, and deep Mystic Well enchant rules.
