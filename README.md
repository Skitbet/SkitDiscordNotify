# 🌐 SkitDiscordNotify https://modrinth.com/plugin/skitdiscordnotify
A simple but handy plugin that sends your Minecraft server’s status updates to a Discord channel using webhooks. Whether you're running a private server or hosting for a community, this helps keep your players or staff in the loop without needing to check the console.
---
## 🔧 What It Does
This plugin listens for server events and sends updates to a Discord channel using a webhook. It supports both **embedded** messages (with color, title, description) and **plain text**. You can choose what gets sent and how it looks, all controlled through a config file!
---
## ✅ Current Features
* Sends messages to Discord on:
  * 🟢 Server start
  * 🔴 Server stop
  * ➕ Player joins
  * ➖ Player leaves
* Choose between plain text or embeds for each event
* Toggle each event individually
* Customize embed title, description, and color for each event individually
* Placeholder `%player%` for player-specific messages, with more coming
---
## 📂 Configuration
When you first run the plugin, it’ll create a config file like this:
```yaml
webhook-url: ""

events:
  server-start:
    enabled: true
    embed: true
    message: "🟢 The server has started!"
    embed-data:
      title: "Server Online"
      description: "Minecraft server has successfully started."
      color: 65280
  **other status settings**
```
Each event has its own section. You can turn them on/off, switch between embed or text, and customize messages. Color is in decimal format (like 65280 = green).
---
## 🔮 Upcoming Features
Here’s what’s planned for the next updates:
* 📊 Online player count pings (notify when server empties or gets busy)
* 💬 Admin commands like `/discordstatus reload` or `/testdiscord`
* ⏲️ Scheduled messages (like hourly uptime posts)
* 🧩 Placeholder support for `%online%`, `%max_players%`, and more
* 🔄 Multiple webhook support (separate channels for different events)
Have a suggestion? Feel free to open an issue or drop feedback on GitHub!
---
## 🧪 Tested On
* Paper 1.21
* Java 21
  (Should work on most modern Bukkit/Spigot/Paper servers!)
---
## 📜 License
MIT. Use it, fork it, remix it, just credit where credit's due.
---
## ❤️ Thanks
Thanks for checking this plugin out! It's a small utility, but hopefully it helps improve your server's communication with players or staff.
