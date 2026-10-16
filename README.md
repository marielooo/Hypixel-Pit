# Hypixel Pit Plugin

A lightweight Java 8 + Spigot 1.8.8 plugin scaffold for a Pit-inspired mechanics system.

## Features
- basic player profile tracking
- XP progression with prestige scaling
- gold and renown persistence
- prestige reset behavior
- `/pitstats`, `/prestige`, `/pithelp` commands

## Build

```bash
mvn clean package
```

This produces a plugin JAR in `target/`.

## Install

1. Place the generated JAR into your Spigot server's `plugins/` directory.
2. Start the server.
3. Verify the plugin loads with Java 8 and the Spigot API.

## Notes
- This scaffold is intentionally based on Spigot 1.8.8 for canonical 1.8.9 PvP compatibility.
- The code is structured so later phases can add Pit systems like passives, perks, megastreaks, and Mystic Well logic.
- For newer server versions (1.8 -> 1.26.2), the `org.spigotmc:spigot-api` dependency would need to be updated and compatibility checks performed before deployment.

## Command examples

```text
/pitstats
/pitstats marielooo
/prestige
/pithelp
```
